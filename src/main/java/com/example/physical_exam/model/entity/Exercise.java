package com.example.physical_exam.model.entity;

import com.example.physical_exam.model.enumeration.ExerciseEnum;
import com.example.physical_exam.model.enumeration.Gender;
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
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_EXERCISE_LENGTH;
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
    @Enumerated(EnumType.STRING)
    @Size(min = 7, max = 10, message = FIELD_VALIDATION_EXERCISE_LENGTH)
    @Column(name = "name")
    private ExerciseEnum name;

    @Positive(message = REQUIREMENT_POSITIVE)
    @Column(name = "requirement")
    private Integer requirement;

    @NotNull(message = EMPLOYEE_GENDER_NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

}
