package com.example.physical_exam.model.dto.request;

import com.example.physical_exam.model.entity.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.RESULT_OF_EXECUTION_POSITIVE;
import static com.example.physical_exam.model.constant.ValidationMessages.YEAR_POSITIVE;

/**
 * Request Dto class that is used for the input of saving {@link Result}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultCreationRequestDto {

    @NotNull(message = FIELD_NOT_NULL)
    private Long employeeId;

    @Positive(message = YEAR_POSITIVE)
    @Min(value = 2000)
    private Integer yearOfPerformance;

    @Positive(message = RESULT_OF_EXECUTION_POSITIVE)
    private Integer runningTimeInSeconds;

    @Positive(message = RESULT_OF_EXECUTION_POSITIVE)
    private Integer crunchesCount;

    @Positive(message = RESULT_OF_EXECUTION_POSITIVE)
    private Integer pushUpsCount;

    @Positive(message = RESULT_OF_EXECUTION_POSITIVE)
    private Integer jumpInCentimeters;

}
