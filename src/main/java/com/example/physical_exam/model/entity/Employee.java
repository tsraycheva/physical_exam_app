package com.example.physical_exam.model.entity;

import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.model.enumeration.Position;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.example.physical_exam.model.constant.ValidationMessages.EMPLOYEE_GENDER_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.EMPLOYEE_IDENTIFICATION_NUMBER_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.EMPLOYEE_IMAGE_URL_NOT_EMPTY;
import static com.example.physical_exam.model.constant.ValidationMessages.EMPLOYEE_POSITION_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_NOT_BLANK;
import static com.example.physical_exam.model.constant.ValidationMessages.IDENTIFICATION_NUMBER_POSITIVE;

/**
 * Entity Class that represents employees table in the physical_exam database
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @NotBlank(message = FIELD_VALIDATION_NAME_NOT_BLANK)
    @Size(min = 3, max = 50, message = FIELD_VALIDATION_NAME_LENGTH)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = FIELD_VALIDATION_NAME_NOT_BLANK)
    @Size(min = 3, max = 50, message = FIELD_VALIDATION_NAME_LENGTH)
    @Column(name = "last_name")
    private String lastName;

    @Positive(message = IDENTIFICATION_NUMBER_POSITIVE)
    @NotNull(message = EMPLOYEE_IDENTIFICATION_NUMBER_NOT_NULL)
    @Column(name = "identification_number", unique = true)
    private Integer identificationNumber;

    @NotBlank(message = EMPLOYEE_IMAGE_URL_NOT_EMPTY)
    @Column(name = "image_url")
    private String imageUrl;

    @NotNull(message = EMPLOYEE_GENDER_NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    @NotNull(message = EMPLOYEE_POSITION_NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "position", length = 22)
    private Position position;

}
