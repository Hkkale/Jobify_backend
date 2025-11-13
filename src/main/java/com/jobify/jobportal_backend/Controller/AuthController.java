package com.jobify.jobportal_backend.Controller;

import com.jobify.jobportal_backend.Jwt.AuthenticationRequest;
import com.jobify.jobportal_backend.Jwt.AuthenticationResponse;
import com.jobify.jobportal_backend.Jwt.JwtHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/auth")
public class AuthController {
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;

    private JwtHelper jwtHelper;


    public AuthController(UserDetailsService userDetailsService, AuthenticationManager authenticationManager, JwtHelper jwtHelper) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }




    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        final UserDetails userDetails=userDetailsService.loadUserByUsername(request.getEmail());
        final String jwt=jwtHelper.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));




    }
}
