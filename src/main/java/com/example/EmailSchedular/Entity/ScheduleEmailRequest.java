package com.example.EmailSchedular.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEmailRequest {
    @Email
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Subject cannot be empty")
    private String subject;

    @NotBlank(message = "Body cannot be empty")
    private String body;

    @NotNull(message = "Scheduled time cannot be null")
    private LocalDateTime scheduledTime;

    @NotNull(message = "Time zone cannot be null")
    private ZoneId timeZone;
}
