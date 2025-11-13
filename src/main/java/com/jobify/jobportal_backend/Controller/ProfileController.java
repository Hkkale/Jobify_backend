package com.jobify.jobportal_backend.Controller;


import com.jobify.jobportal_backend.DTOs.ProfileDto;
import com.jobify.jobportal_backend.DTOs.ResponseDto;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import com.jobify.jobportal_backend.Service.ProfileService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@Validated
@RequestMapping("/profiles")
public class ProfileController {

    private ProfileService profileService;


    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable  Long id ) throws JobPortalException {




        return new ResponseEntity<>(profileService.getProfile(id), HttpStatus.OK);







    }

    @GetMapping("/get")
    public ResponseEntity<List<ProfileDto>> getAllProfiles( ) throws JobPortalException {




        return new ResponseEntity<>(profileService.getAllProfiles(), HttpStatus.OK);







    }

    @PutMapping("/update")
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody  ProfileDto profileDto ) throws JobPortalException {




        return new ResponseEntity<>(profileService.updateProfile(profileDto), HttpStatus.OK);







    }


    







}
