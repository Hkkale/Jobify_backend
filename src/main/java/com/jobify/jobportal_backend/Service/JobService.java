package com.jobify.jobportal_backend.Service;

import com.jobify.jobportal_backend.DTOs.JobDto;
import com.jobify.jobportal_backend.Exception.JobPortalException;

public interface JobService {

    JobDto postJob( JobDto jobDto) throws JobPortalException;
}
