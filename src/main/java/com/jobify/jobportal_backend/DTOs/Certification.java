package com.jobify.jobportal_backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Certification {
    private String title;
    private String issuer;
    private LocalDateTime issueDate;
    private String certificateId;
}
