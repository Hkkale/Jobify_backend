package com.jobify.jobportal_backend.Service;


import com.jobify.jobportal_backend.DTOs.NotificationDto;
import com.jobify.jobportal_backend.DTOs.NotificationStatus;
import com.jobify.jobportal_backend.Entity.Notification;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import com.jobify.jobportal_backend.Repository.NotificationRepository;
import com.jobify.jobportal_backend.Utility.Utilities;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final Utilities utilities;

    public NotificationServiceImpl(NotificationRepository notificationRepository, Utilities utilities) {
        this.notificationRepository = notificationRepository;
        this.utilities = utilities;
    }

    @Override
    public void sendNotification(NotificationDto notificationDto) throws JobPortalException {

        notificationDto.setId(utilities.getNextSequence("notification"));
        notificationDto.setStatus(NotificationStatus.UNREAD);
        notificationDto.setTimeStamp(LocalDateTime.now());
        notificationRepository.save(notificationDto.toEntity());

    }

    @Override
    public List<Notification> getUnreadNotification(Long userId) {
        return notificationRepository.findByUserIdAndStatus(userId, NotificationStatus.UNREAD);
    }

    @Override
    public void readNotification(Long userId) throws JobPortalException {
        Notification notf=notificationRepository.findById(userId).orElseThrow(()-> new JobPortalException("No Notification Found"));

        notf.setStatus(NotificationStatus.READ);
        notificationRepository.save(notf);
       
    }
}
