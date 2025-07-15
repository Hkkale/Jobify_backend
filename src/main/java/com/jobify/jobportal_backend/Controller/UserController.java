package com.jobify.jobportal_backend.Controller;


import com.jobify.jobportal_backend.DTOs.LoginDto;
import com.jobify.jobportal_backend.DTOs.ResponseDto;
import com.jobify.jobportal_backend.DTOs.UserDto;

import com.jobify.jobportal_backend.Exception.JobPortalException;
import com.jobify.jobportal_backend.Service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/users")

public class UserController {

    private  UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser( @RequestBody @Valid UserDto userDto) throws JobPortalException {


        userDto= userService.registerUser(userDto);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);







    }



    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser( @RequestBody @Valid LoginDto loginDto) throws JobPortalException {




        return new ResponseEntity<>(userService.loginUser(loginDto), HttpStatus.OK);







    }


    @PostMapping("/sendOtp/{email}")
    public ResponseEntity<ResponseDto> sendOtp(@PathVariable @Email(message = "{user.email.invalid}") String email) throws Exception {


        userService.sendOtp(email);

        return new ResponseEntity<>(new ResponseDto("Otp sent successfully"), HttpStatus.OK);







    }


    @GetMapping("/verifyOtp/{email}/{otp}")
    public ResponseEntity<ResponseDto> verifyOtp(@PathVariable @Email(message = "{user.email.invalid}") String email, @PathVariable @Pattern(regexp = "^[0-9]{6}$",message = "{otp.invalid}")  String otp) throws JobPortalException {


        userService.verifyOtp(email,otp);

        return new ResponseEntity<>(new ResponseDto("Otp has been verified!"), HttpStatus.OK);







    }

}
