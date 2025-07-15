package com.jobify.jobportal_backend.Utility;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private Environment environment;

    public ExceptionControllerAdvice(Environment environment) {
        this.environment = environment;
    }


    @ExceptionHandler(Exception.class)

    public ResponseEntity<ErrorInfo> generalException(Exception exception){

        ErrorInfo error = ErrorInfo.builder()
                .errorMessage(exception.getMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timeStamp(LocalDateTime.now())
                .build();


        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);




    }

    @ExceptionHandler(JobPortalException.class)

    public ResponseEntity<ErrorInfo> generalException(JobPortalException exception){

        String msg=environment.getProperty(exception.getMessage());
        ErrorInfo error = ErrorInfo.builder()
                .errorMessage(msg)
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timeStamp(LocalDateTime.now())
                .build();


        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);




    }


    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})

    public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception){

        String msg="";
        if(exception instanceof MethodArgumentNotValidException manvException){
            msg=manvException.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));

        }
        else{
            ConstraintViolationException cvException=(ConstraintViolationException) exception;
            msg=cvException.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
        }


        ErrorInfo error = ErrorInfo.builder()
                .errorMessage(msg)
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();


        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

    }

    }
