package com.jobify.jobportal_backend.DTOs;

import com.jobify.jobportal_backend.Entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {


    private Long id;
    private Long userId;
    private String message;
    private String action;
    private String routes;
    private LocalDateTime timeStamp;
    private NotificationStatus status;


    public Notification toEntity(){

        return new Notification(this.id,this.userId,this.message,this.action,this.routes,this.timeStamp,this.status);

    }
}
