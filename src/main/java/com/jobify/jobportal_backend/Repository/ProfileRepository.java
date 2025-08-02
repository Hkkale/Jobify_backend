package com.jobify.jobportal_backend.Repository;

import com.jobify.jobportal_backend.Entity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfileRepository extends MongoRepository<Profile,Long> {
}
