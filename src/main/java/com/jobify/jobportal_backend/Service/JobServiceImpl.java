package com.jobify.jobportal_backend.Service;

import com.jobify.jobportal_backend.DTOs.JobDto;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import com.jobify.jobportal_backend.Repository.JobRepository;
import com.jobify.jobportal_backend.Utility.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("jobService")
public class JobServiceImpl implements JobService{

    private JobRepository jobRepository;
    private Utilities utilities;


    @Autowired
    public JobServiceImpl(JobRepository jobRepository, Utilities utilities) {
        this.jobRepository = jobRepository;
        this.utilities = utilities;
    }


    @Override
    public JobDto postJob(JobDto jobDto) throws JobPortalException {
        jobDto.setId(utilities.getNextSequence(("jobs")));
        jobDto.setPostTime(LocalDateTime.now());
        return jobRepository.save(jobDto.toEntity()).toDto();
    }
}
