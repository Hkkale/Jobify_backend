package com.jobify.jobportal_backend.Entity;

import com.jobify.jobportal_backend.DTOs.ApplicantDto;
import com.jobify.jobportal_backend.DTOs.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {

    private Long applicantId;
    private String name;
    private String email;
    private String phone;
    private String website;
    private byte[] resume;
    private String coverLetter;
    private LocalDateTime timeStamp;

    private ApplicationStatus applicationStatus;
    private LocalDateTime interviewTime;


    public ApplicantDto toDto(){

        return new ApplicantDto(this.applicantId,this.name,this.email,this.phone,this.website,this.resume!=null ? Base64.getEncoder().encodeToString(this.resume):null,this.coverLetter,this.timeStamp,this.applicationStatus,this.interviewTime);

    }

}
