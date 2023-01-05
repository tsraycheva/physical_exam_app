package com.example.physical_exam.service;

import com.example.physical_exam.model.dto.request.AuthenticationRequest;
import com.example.physical_exam.model.dto.request.UserRegisterRequest;
import com.example.physical_exam.model.dto.response.AuthenticationResponse;
import com.example.physical_exam.model.entity.User;
/**
 * Class that holds logic related to register and authenticate user
 */
public interface AuthenticationService {

    /**
     * Method that accepts {@link UserRegisterRequest} and saves the user to the DB
     *
     * @param userRegisterRequest {@link UserRegisterRequest} with all needed data for register a {@link User}
     * @return {@link AuthenticationResponse} containing JWT
     */
    AuthenticationResponse register(UserRegisterRequest userRegisterRequest);

    /**
     * Method that accepts {@link AuthenticationRequest} and checks if the {@link User} is authenticated
     *
     * @param authenticationRequest {@link AuthenticationRequest} with all needed data authenticate a {@link User}
     * @return {@link AuthenticationResponse} containing JWT
     */
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
