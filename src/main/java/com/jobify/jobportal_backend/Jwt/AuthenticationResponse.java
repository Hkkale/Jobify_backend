package com.jobify.jobportal_backend.Jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class AuthenticationResponse {

    private final String jwt;
}
