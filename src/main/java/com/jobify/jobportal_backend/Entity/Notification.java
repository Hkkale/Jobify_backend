package com.jobify.jobportal_backend.Entity;

import com.jobify.jobportal_backend.DTOs.NotificationDto;
import com.jobify.jobportal_backend.DTOs.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private Long id;
    private Long userId;
    private String message;
    private String action;
    private String routes;
    private LocalDateTime timeStamp;
    private NotificationStatus status;



    public NotificationDto toDto(){

        return new NotificationDto(this.id,this.userId,this.message,this.action,this.routes,this.timeStamp,this.status);

    }
}
