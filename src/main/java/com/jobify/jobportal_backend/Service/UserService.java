package com.jobify.jobportal_backend.Service;

import com.jobify.jobportal_backend.DTOs.LoginDto;
import com.jobify.jobportal_backend.DTOs.ResponseDto;
import com.jobify.jobportal_backend.DTOs.UserDto;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import jakarta.mail.MessagingException;

public interface UserService {
    public UserDto registerUser(UserDto userDto) throws JobPortalException;

    UserDto loginUser(LoginDto loginDto) throws JobPortalException;

    public UserDto getUserByEmail(String email) throws JobPortalException;


    Boolean sendOtp(String email) throws Exception;

    Boolean verifyOtp(String email, String otp) throws JobPortalException;

    ResponseDto changePassword(LoginDto loginDto) throws JobPortalException;
}
