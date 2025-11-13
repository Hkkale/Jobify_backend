package com.jobify.jobportal_backend.Service;

import com.jobify.jobportal_backend.DTOs.ApplicantDto;
import com.jobify.jobportal_backend.DTOs.Application;
import com.jobify.jobportal_backend.DTOs.JobDto;
import com.jobify.jobportal_backend.DTOs.ResponseDto;
import com.jobify.jobportal_backend.Exception.JobPortalException;

import java.util.List;

public interface JobService {

    JobDto postJob( JobDto jobDto) throws JobPortalException;

    List<JobDto> getAllJobs();

    JobDto getJob(Long id) throws JobPortalException;

    void applyJob(Long id, ApplicantDto applicantDto) throws  JobPortalException;

    List<JobDto> getJobsPostedBy(Long id);

    void changeAppStatus(Application application) throws JobPortalException;
}
