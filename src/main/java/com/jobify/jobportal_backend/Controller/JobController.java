package com.jobify.jobportal_backend.Controller;


import com.jobify.jobportal_backend.DTOs.JobDto;
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












}
