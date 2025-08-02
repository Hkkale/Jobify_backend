package com.jobify.jobportal_backend.Repository;

import com.jobify.jobportal_backend.Entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job,Long> {
}
