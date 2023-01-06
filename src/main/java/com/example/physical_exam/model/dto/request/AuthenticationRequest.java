package com.example.physical_exam.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_PASSWORD_MIN_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_USERNAME_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_USERNAME_NOT_BLANK;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = FIELD_VALIDATION_USERNAME_NOT_BLANK)
    @Size(min = 10, max = 50, message = FIELD_VALIDATION_USERNAME_LENGTH)
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = FIELD_NOT_NULL)
    @Size(min = 3, message = FIELD_PASSWORD_MIN_LENGTH)
    @Column(name = "password")
    private String password;
}
