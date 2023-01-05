package com.example.physical_exam.web;

import com.example.physical_exam.model.dto.request.AuthenticationRequest;
import com.example.physical_exam.model.dto.request.UserRegisterRequest;
import com.example.physical_exam.model.dto.response.AuthenticationResponse;
import com.example.physical_exam.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller with endpoints related to Register and Authentication users
 */
@RestController
@RequestMapping("/api/v1/physical_exam/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(authenticationService.register(userRegisterRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
