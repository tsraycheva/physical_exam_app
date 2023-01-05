package com.example.physical_exam.service.impl;

import com.example.physical_exam.model.dto.request.AuthenticationRequest;
import com.example.physical_exam.model.dto.request.UserRegisterRequest;
import com.example.physical_exam.model.dto.response.AuthenticationResponse;
import com.example.physical_exam.model.entity.User;
import com.example.physical_exam.model.enumeration.Role;
import com.example.physical_exam.repository.UserRepository;
import com.example.physical_exam.security.JwtService;
import com.example.physical_exam.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(UserRegisterRequest userRegisterRequest) {

        log.info("user with firstname {}, lastname {}, username {}, password {} and position is trying to register",
                userRegisterRequest.getFirstName(),
                userRegisterRequest.getLastName(),
                userRegisterRequest.getUsername(),
                userRegisterRequest.getPassword());

        var user = User.builder()
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .username(userRegisterRequest.getUsername())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                //TODO add position to USer Entity and choose the role that corresponds to the position
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        log.info("user with id {}, firstname {}, lastname {}, username {}, password {} and position is saved successfully!",

                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getUsername(),
                savedUser.getPassword());

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));

        var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with username %s not found!", authenticationRequest.getUsername())));


        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}