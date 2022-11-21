package com.example.physical_exam.model.dto.response;

import com.example.physical_exam.model.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Response Dto class that is used for the output of obtaining {@link Employee} with results
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResultsResponseDto extends BaseResponseDto {

    private String employeeNames;
    private Integer identificationNumber;
    private List<ResultResponseDto> resultResponseDto;

}
