package com.example.EmailSchedular.Controller;


import com.example.EmailSchedular.Entity.ScheduleEmailRequest;
import com.example.EmailSchedular.Entity.ScheduleEmailResponse;
import com.example.EmailSchedular.Job.EmailJob;
import jakarta.validation.Valid;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/emails")
public class EmailSchedulerController {

    private static final Logger logger = LoggerFactory.getLogger(EmailSchedulerController.class);
    private final Scheduler scheduler;


    public EmailSchedulerController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleEmailResponse> scheduleEmail(@Valid @RequestBody ScheduleEmailRequest request) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(request.getScheduledTime(), request.getTimeZone());

            // Validate if the scheduled time is in the future
            if (dateTime.isBefore(ZonedDateTime.now())) {
                return ResponseEntity.badRequest().body(new ScheduleEmailResponse(false, "Scheduled time must be in the future."));
            }

            // Create job and trigger
            JobDetail jobDetail = buildJobDetail(request);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);

            logger.info("✅ Email scheduled successfully! Job ID: {}, Group: {}", jobDetail.getKey().getName(), jobDetail.getKey().getGroup());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ScheduleEmailResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Email scheduled successfully!"));
        } catch (SchedulerException e) {
            logger.error("❌ Error scheduling email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ScheduleEmailResponse(false, "Error scheduling email. Please try again later."));
        }
    }

    private JobDetail buildJobDetail(ScheduleEmailRequest request) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("email", request.getEmail());
        jobDataMap.put("subject", request.getSubject());
        jobDataMap.put("body", request.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
