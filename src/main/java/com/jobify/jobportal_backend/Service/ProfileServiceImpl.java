package com.jobify.jobportal_backend.Service;

import com.jobify.jobportal_backend.DTOs.AccountType;
import com.jobify.jobportal_backend.DTOs.ProfileDto;
import com.jobify.jobportal_backend.Entity.Profile;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import com.jobify.jobportal_backend.Repository.ProfileRepository;
import com.jobify.jobportal_backend.Utility.Utilities;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("profileService")
public class ProfileServiceImpl implements  ProfileService{

    private Utilities utilities;
    private ProfileRepository profileRepository;

    public ProfileServiceImpl(Utilities utilities, ProfileRepository profileRepository) {
        this.utilities = utilities;
        this.profileRepository = profileRepository;
    }

    @Override
    public Long createProfile(String email,String name,AccountType accountType) throws JobPortalException {


        Profile profile = Profile.builder()
                .id(utilities.getNextSequence("profiles"))
                .name(name)
                .email(email)
                .accountType(accountType)
                .skills(new ArrayList<>())
                .experiences(new ArrayList<>())
                .certifications(new ArrayList<>())
                .savedJobs(new ArrayList<>())
                .build();

        profileRepository.save(profile);




        return profile.getId();
    }

    @Override
    public ProfileDto getProfile(Long id) throws JobPortalException {
        return profileRepository.findById(id).orElseThrow(()->new JobPortalException("PROFILE_NOT_FOUND")).toDto();
    }

    @Override
    public ProfileDto updateProfile(ProfileDto profileDto) throws JobPortalException {
        profileRepository.findById(profileDto.getId()).orElseThrow(()->new JobPortalException("PROFILE_NOT_FOUND"));

        profileRepository.save(profileDto.toEntity());

        return profileDto;


    }

    @Override
    public List<ProfileDto> getAllProfiles()  {
        return profileRepository.findAll().stream().map((x)->x.toDto()).toList();
    }
}
