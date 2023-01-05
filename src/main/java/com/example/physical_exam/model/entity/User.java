package com.example.physical_exam.model.entity;

import com.example.physical_exam.model.enumeration.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_EMAIL_NOT_VALID;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_PASSWORD_MIN_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_EMAIL_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_EMAIL_NOT_BLANK;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_NOT_BLANK;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class User extends BaseEntity {

    @NotBlank(message = FIELD_VALIDATION_NAME_NOT_BLANK)
    @Size(min = 3, max = 50, message = FIELD_VALIDATION_NAME_LENGTH)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = FIELD_VALIDATION_NAME_NOT_BLANK)
    @Size(min = 3, max = 50, message = FIELD_VALIDATION_NAME_LENGTH)
    @Column(name = "last_name")
    private String lastName;

    @Email(message = FIELD_EMAIL_NOT_VALID)
    @NotBlank(message = FIELD_VALIDATION_EMAIL_NOT_BLANK)
    @Size(min = 3, max = 50, message = FIELD_VALIDATION_EMAIL_LENGTH)
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = FIELD_NOT_NULL)
    @Size(min = 3, message = FIELD_PASSWORD_MIN_LENGTH)
    @Column(name = "password")
    private String password;

    @NotNull(message = FIELD_NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 5)
    private Role role;

}
