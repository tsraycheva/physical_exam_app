package com.example.physical_exam.model.dto.request;

import com.example.physical_exam.model.enumeration.Position;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.physical_exam.model.constant.ValidationMessages.EMPLOYEE_POSITION_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_PASSWORD_MIN_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_NOT_BLANK;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_USERNAME_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_USERNAME_NOT_BLANK;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {

    @NotBlank(message = FIELD_VALIDATION_NAME_NOT_BLANK)
    @Size(min = 3, max = 50, message = FIELD_VALIDATION_NAME_LENGTH)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = FIELD_VALIDATION_NAME_NOT_BLANK)
    @Size(min = 3, max = 50, message = FIELD_VALIDATION_NAME_LENGTH)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = FIELD_VALIDATION_USERNAME_NOT_BLANK)
    @Size(min = 10, max = 50, message = FIELD_VALIDATION_USERNAME_LENGTH)
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = FIELD_NOT_NULL)
    @Size(min = 3, message = FIELD_PASSWORD_MIN_LENGTH)
    @Column(name = "password")
    private String password;

    @NotNull(message = EMPLOYEE_POSITION_NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "position", length = 33)
    private Position position;
}
