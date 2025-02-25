package com.example.EmailSchedular.Job;


import com.example.EmailSchedular.Service.EmailService;
import jakarta.mail.MessagingException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailJob implements Job {

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        String to = context.getJobDetail().getJobDataMap().getString("to");
        String subject = context.getJobDetail().getJobDataMap().getString("subject");
        String text = context.getJobDetail().getJobDataMap().getString("text");

        try {
            emailService.sendEmail(to, subject, text);
        } catch (MessagingException e) {
            throw new JobExecutionException(e);
        }
    }
}


