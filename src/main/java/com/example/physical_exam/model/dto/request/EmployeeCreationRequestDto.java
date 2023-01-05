package com.example.physical_exam.model.dto.request;

import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.model.enumeration.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.physical_exam.model.constant.ValidationMessages.EMPLOYEE_GENDER_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.EMPLOYEE_IDENTIFICATION_NUMBER_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.EMPLOYEE_IMAGE_URL_NOT_EMPTY;
import static com.example.physical_exam.model.constant.ValidationMessages.EMPLOYEE_POSITION_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_NOT_BLANK;
import static com.example.physical_exam.model.constant.ValidationMessages.IDENTIFICATION_NUMBER_POSITIVE;

/**
 * Request Dto class that is used for the input of saving {@link Employee}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreationRequestDto {

    @NotBlank(message = FIELD_VALIDATION_NAME_NOT_BLANK)
    @Size(min = 3, max = 50, message = FIELD_VALIDATION_NAME_LENGTH)
    private String firstName;

    @NotBlank(message = FIELD_VALIDATION_NAME_NOT_BLANK)
    @Size(min = 3, max = 50, message = FIELD_VALIDATION_NAME_LENGTH)
    private String lastName;

    @Positive(message = IDENTIFICATION_NUMBER_POSITIVE)
    @NotNull(message = EMPLOYEE_IDENTIFICATION_NUMBER_NOT_NULL)
    private Integer identificationNumber;

    @NotBlank(message = EMPLOYEE_IMAGE_URL_NOT_EMPTY)
    private String imageUrl;

    @NotNull(message = EMPLOYEE_GENDER_NOT_NULL)
    private Gender gender;

    @NotNull(message = EMPLOYEE_POSITION_NOT_NULL)
    private Position position;
}
