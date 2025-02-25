package com.example.EmailSchedular.Service;


import com.example.EmailSchedular.Job.EmailJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ScheduledEmailService {

    @Autowired
    private Scheduler scheduler;

    public void scheduleEmail(String to, String subject, String text, Date sendTime) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(EmailJob.class)
                .withIdentity("emailJob", "emailGroup")
                .usingJobData("to", to)
                .usingJobData("subject", subject)
                .usingJobData("text", text)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("emailTrigger", "emailGroup")
                .startAt(sendTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
