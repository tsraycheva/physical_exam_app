package com.example.physical_exam.model.entity;

import com.example.physical_exam.model.enumeration.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_PASSWORD_MIN_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_NAME_NOT_BLANK;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_USERNAME_LENGTH;
import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_VALIDATION_USERNAME_NOT_BLANK;

/**
 * Class that represents users who will operate with the application. If they are employees from Human Resources Department
 * they will be authorized to store new employees and results (their role will be ADMIN). The Chiefs and Heads of Fire Departments
 * will only have the rights to obtain information and not to change it (they will be with role USER).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

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

    @NotNull(message = FIELD_NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 5)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
