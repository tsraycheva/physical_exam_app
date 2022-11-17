package com.example.physical_exam.model.dto.response;

import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.model.enumeration.Position;
import com.example.physical_exam.model.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response Dto class that is used for the output of obtaining {@link Employee}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto extends BaseResponseDto {

    private String firstName;
    private String lastName;
    private Integer identificationNumber;
    private String imageUrl;
    private Gender gender;
    private Position position;
}
