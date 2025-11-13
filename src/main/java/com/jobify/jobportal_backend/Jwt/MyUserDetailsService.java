package com.jobify.jobportal_backend.Jwt;

import com.jobify.jobportal_backend.DTOs.UserDto;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import com.jobify.jobportal_backend.Service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            UserDto userDto= userService.getUserByEmail(email);
            return new CustomUserDetails(userDto.getId(),email,userDto.getName(),userDto.getPassword(),userDto.getProfileId(),userDto.getAccountType(),new ArrayList<>());
        } catch (JobPortalException e) {
            e.printStackTrace();
        }

        return null;
    }
}
