package com.jobify.jobportal_backend.Utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorInfo {

    private String errorMessage;
    private Integer errorCode;
    private LocalDateTime timeStamp;
}
