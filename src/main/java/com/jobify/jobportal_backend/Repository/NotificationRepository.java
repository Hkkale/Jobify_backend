package com.jobify.jobportal_backend.Repository;

import com.jobify.jobportal_backend.DTOs.NotificationStatus;
import com.jobify.jobportal_backend.Entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository  extends MongoRepository<Notification,Long> {
    public List<Notification> findByUserIdAndStatus(Long userId, NotificationStatus status );
}
