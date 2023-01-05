package com.example.physical_exam.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    //TODO - fields validation
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
