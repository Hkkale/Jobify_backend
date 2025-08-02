package com.jobify.jobportal_backend.DTOs;

import com.jobify.jobportal_backend.Entity.Job;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDto {

    private Long id;
    private String jobTitle;
    private String company;
    private List<Applicant> applicants;
    private String about;
    private String experience;
    private String jobType;
    private String location;
    private Long packageOffered;
    private LocalDateTime postTime;
    private String description;
    private List<String> skillsRequired;
    private JobStatus jobstatus;

    public Job toEntity(){
        return new Job(this.id,this.jobTitle,this.company,this.applicants,
                this.about,this.experience,this.jobType,this.location,this.packageOffered,this.postTime,this.description,this.skillsRequired,this.jobstatus);
    }
}
