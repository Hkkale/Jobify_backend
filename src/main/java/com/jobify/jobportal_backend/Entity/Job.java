package com.jobify.jobportal_backend.Entity;


import com.jobify.jobportal_backend.DTOs.Applicant;
import com.jobify.jobportal_backend.DTOs.JobDto;
import com.jobify.jobportal_backend.DTOs.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {

    @Id
    private Long id;
    private String jobTitle;
    private String company;
    private List<Applicant>applicants;
    private String about;
    private String experience;
    private String jobType;
    private String location;
    private Long packageOffered;
    private LocalDateTime postTime;
    private String description;
    private List<String> skillsRequired;
    private JobStatus jobstatus;

    public JobDto toDto(){
        return new JobDto(this.id,this.jobTitle,this.company,this.applicants,
                this.about,this.experience,this.jobType,this.location,this.packageOffered,this.postTime,this.description,this.skillsRequired,this.jobstatus);
    }

}
