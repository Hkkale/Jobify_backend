package com.jobify.jobportal_backend.Service;

import com.jobify.jobportal_backend.DTOs.*;
import com.jobify.jobportal_backend.Entity.Applicant;
import com.jobify.jobportal_backend.Entity.Job;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import com.jobify.jobportal_backend.Repository.JobRepository;
import com.jobify.jobportal_backend.Utility.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("jobService")
public class JobServiceImpl implements JobService{

    private JobRepository jobRepository;
    private Utilities utilities;

    private NotificationService notificationService;


    @Autowired
    public JobServiceImpl(JobRepository jobRepository, Utilities utilities, NotificationService notificationService) {
        this.jobRepository = jobRepository;
        this.utilities = utilities;
        this.notificationService = notificationService;
    }


    @Override
    public JobDto postJob(JobDto jobDto) throws JobPortalException {
        if(jobDto.getId()==0){
            jobDto.setId(utilities.getNextSequence(("jobs")));
            jobDto.setPostTime(LocalDateTime.now());
            NotificationDto notfDto= new NotificationDto();
            notfDto.setAction("Job Posted");
            notfDto.setMessage("Job posted successfully for : "+jobDto.getJobTitle()+" at "+jobDto.getCompany());
            notfDto.setUserId(jobDto.getPostedBy());
            notfDto.setRoutes("/posted-jobs/"+jobDto.getId());
            try {
                notificationService.sendNotification(notfDto);
            } catch (JobPortalException e) {
                throw new RuntimeException(e);
            }

        }
        else{

            Job job =jobRepository.findById(jobDto.getId()).orElseThrow(()->new JobPortalException("JOB_NOT_FOUND"));
            if(job.getJobStatus().equals(JobStatus.DRAFT)|| jobDto.getJobStatus().equals(JobStatus.CLOSED)) {
                jobDto.setPostTime(LocalDateTime.now());
            }

        }

        return jobRepository.save(jobDto.toEntity()).toDto();
    }

    @Override
    public List<JobDto> getAllJobs() {
        return jobRepository.findAll().stream()
                .map((x)->x.toDto()).toList();
    }

    @Override
    public JobDto getJob(Long id) throws JobPortalException {
        return jobRepository.findById(id).orElseThrow(()->new JobPortalException("JOB_NOT_FOUND")).toDto();
    }

    @Override
    public void applyJob(Long id, ApplicantDto applicantDto) throws JobPortalException {
        Job job= jobRepository.findById(id).orElseThrow(()->new JobPortalException("JOB_NOT_FOUND"));
        List<Applicant> applicants=job.getApplicants();
        if(applicants==null) applicants=new ArrayList<>();

        if(!applicants.stream().filter((x) -> x.getApplicantId() == applicantDto.getApplicantId()).toList().isEmpty()) throw new JobPortalException("JOB_ALREADY_APPLIED");


        applicantDto.setApplicationStatus(ApplicationStatus.APPLIED);
        applicants.add(applicantDto.toEntity());
        job.setApplicants(applicants);
        jobRepository.save(job);
    }

    @Override
    public List<JobDto> getJobsPostedBy(Long id) {

        return jobRepository.findByPostedBy(id).stream().map((x)->x.toDto()).toList() ;
    }

    @Override
    public void changeAppStatus(Application application) throws JobPortalException {
        Job job= jobRepository.findById(application.getId()).orElseThrow(()->new JobPortalException("JOB_NOT_FOUND"));
        List<Applicant> applicants=job.getApplicants().stream()
                .map((x)->{
                    if(application.getApplicantId()==x.getApplicantId()){
                        x.setApplicationStatus(application.getApplicationStatus());
                        if(application.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING)){
                            x.setInterviewTime(application.getInterviewTime());

                            NotificationDto notfDto= new NotificationDto();
                            notfDto.setAction("Interview Scheduled");
                            notfDto.setMessage("Interview scheduled for job id :"+application.getId());
                            notfDto.setUserId(application.getApplicantId());
                            notfDto.setRoutes("/job-history");
                            try {
                                notificationService.sendNotification(notfDto);
                            } catch (JobPortalException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }



                    return x;

                }).toList();

        job.setApplicants(applicants);
        jobRepository.save(job);
    }
}
