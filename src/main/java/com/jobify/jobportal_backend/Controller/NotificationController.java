package com.jobify.jobportal_backend.Controller;


import com.jobify.jobportal_backend.DTOs.ResponseDto;
import com.jobify.jobportal_backend.Entity.Notification;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import com.jobify.jobportal_backend.Service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin("http://localhost:5173")
@Validated
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }




    @GetMapping("/get/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long userId){
        return new ResponseEntity<>(notificationService.getUnreadNotification(userId), HttpStatus.OK);
    }


    @PutMapping("read/{userId}")
    public ResponseEntity<ResponseDto> readNotification(@PathVariable Long userId) throws JobPortalException {
        //TODO: process PUT request
        
        notificationService.readNotification(userId);


        
        return new ResponseEntity<>(new ResponseDto("Success"),HttpStatus.OK);
    }
}
