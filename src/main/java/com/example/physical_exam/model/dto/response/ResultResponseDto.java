package com.example.physical_exam.model.dto.response;

import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.model.enumeration.Conclusion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response Dto class that is used for the output of obtaining {@link Result}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponseDto {

    private String employeeNames;
    private Integer yearOfPerformance;
    private Integer runningTimeInSeconds;
    private Integer crunchesCount;
    private Integer pushUpsCount;
    private Integer jumpInCentimeters;
    private Conclusion conclusion;
}
