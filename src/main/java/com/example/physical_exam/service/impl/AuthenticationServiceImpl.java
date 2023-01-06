package com.example.physical_exam.service.impl;

import com.example.physical_exam.exception.CanNotPerformOperationException;
import com.example.physical_exam.model.dto.request.AuthenticationRequest;
import com.example.physical_exam.model.dto.request.UserRegisterRequestDto;
import com.example.physical_exam.model.dto.response.AuthenticationResponse;
import com.example.physical_exam.model.entity.User;
import com.example.physical_exam.model.enumeration.Position;
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
    public AuthenticationResponse register(UserRegisterRequestDto userRegisterRequest) {
        Position userPosition = userRegisterRequest.getPosition();

        log.info("user with firstname {}, lastname {}, username {}, password {} and position {} is trying to register",
                userRegisterRequest.getFirstName(),
                userRegisterRequest.getLastName(),
                userRegisterRequest.getUsername(),
                userRegisterRequest.getPassword(),
                userPosition);

        if (existsUsernameInDatabase(userRegisterRequest.getUsername())) {
            throw new CanNotPerformOperationException("The username is already in use by another user! Please choose unique username!");
        }

        Role userRole;

        if (userPosition.equals(Position.HEAD_OF_FIRE_DEPARTMENT)) {
            userRole = Role.USER;
        } else {
            userRole = Role.ADMIN;
        }

        User user = User.builder()
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .username(userRegisterRequest.getUsername())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .role(userRole)
                .position(userPosition)
                .build();

        User savedUser = userRepository.save(user);

        log.info("user with id {}, firstname {}, lastname {}, username {}, password {} and position is saved successfully!",

                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getUsername(),
                savedUser.getPassword());

        String jwtToken = jwtService.generateToken(user);

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

        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with username %s not found!", authenticationRequest.getUsername())));


        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    private boolean existsUsernameInDatabase(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
