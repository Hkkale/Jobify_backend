package com.jobify.jobportal_backend.Service;


import com.jobify.jobportal_backend.DTOs.NotificationDto;
import com.jobify.jobportal_backend.Entity.Notification;
import com.jobify.jobportal_backend.Exception.JobPortalException;

import java.util.List;

public interface NotificationService {


    public void sendNotification(NotificationDto notificationDto) throws JobPortalException;
    public List<Notification> getUnreadNotification(Long userId);

    public void readNotification(Long userId) throws JobPortalException;





}
