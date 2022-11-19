package com.example.physical_exam.model.entity;

import com.example.physical_exam.model.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import static com.example.physical_exam.model.constant.ValidationMessages.EMPLOYEE_GENDER_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_NOT_BLANK;
import static com.example.physical_exam.model.constant.ValidationMessages.REQUIREMENT_POSITIVE;

/**
 * Entity Class that represents exercises table in the physical_exam database
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "exercises")
public class Exercise extends BaseEntity {

    @NotBlank(message = FIELD_VALIDATION_NAME_NOT_BLANK)
    @Size(min = 5, max = 50, message = FIELD_VALIDATION_NAME_LENGTH)
    @Column(name = "name")
    private String name;

    @Positive(message = REQUIREMENT_POSITIVE)
    @Column(name = "requirement")
    private Integer requirement;

    @NotNull(message = EMPLOYEE_GENDER_NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

}
