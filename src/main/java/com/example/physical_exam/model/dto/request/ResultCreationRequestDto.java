package com.example.physical_exam.model.dto.request;

import com.example.physical_exam.model.entity.Result;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Min(value = 0, message = RESULT_OF_EXECUTION_POSITIVE)
    private Integer runningTimeInSeconds;

    @Min(value = 0, message = RESULT_OF_EXECUTION_POSITIVE)
    private Integer crunchesCount;

    @Min(value = 0, message = RESULT_OF_EXECUTION_POSITIVE)
    private Integer pushUpsCount;

    @Min(value = 0, message = RESULT_OF_EXECUTION_POSITIVE)
    private Integer jumpInCentimeters;

}
