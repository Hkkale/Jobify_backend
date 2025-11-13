package com.jobify.jobportal_backend.Controller;


import com.jobify.jobportal_backend.DTOs.ApplicantDto;
import com.jobify.jobportal_backend.DTOs.Application;
import com.jobify.jobportal_backend.DTOs.JobDto;
import com.jobify.jobportal_backend.DTOs.ResponseDto;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import com.jobify.jobportal_backend.Service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @PostMapping("/post")
    public ResponseEntity<JobDto> postJobs(@RequestBody @Valid JobDto jobDto) throws JobPortalException {


        return  new ResponseEntity<>(jobService.postJob(jobDto), HttpStatus.CREATED);

    }



    @GetMapping("/getAll")
    public ResponseEntity<List<JobDto>> getAllJobs() throws JobPortalException {


        return  new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);

    }


    @GetMapping("/get/{id}")
    public ResponseEntity<JobDto> getJob(@PathVariable Long id) throws JobPortalException {


        return  new ResponseEntity<>(jobService.getJob(id), HttpStatus.OK);

    }

    @PostMapping("/apply/{id}")
    public ResponseEntity<ResponseDto> applyJob(@PathVariable Long id, @RequestBody ApplicantDto applicantDto) throws JobPortalException {
        jobService.applyJob(id,applicantDto);

        return  new ResponseEntity<>(new ResponseDto("Applied Successfully"), HttpStatus.OK);

    }

    @GetMapping("/postedBy/{id}")
    public ResponseEntity<List<JobDto>> getJobsPostedBy(@PathVariable Long id) throws JobPortalException {


        return  new ResponseEntity<>(jobService.getJobsPostedBy(id), HttpStatus.OK);

    }

    @PostMapping("/changeAppStatus")
    public ResponseEntity<ResponseDto> changeAppStatus(@RequestBody Application application) throws JobPortalException {
        jobService.changeAppStatus(application);

        return  new ResponseEntity<>(new ResponseDto("Application Changed Successfully"), HttpStatus.OK);

    }





















}
