package com.example.physical_exam.model.entity;

import com.example.physical_exam.model.enumeration.Conclusion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.example.physical_exam.model.constant.EntityValidationMessages.FIELD_NOT_NULL;
import static com.example.physical_exam.model.constant.EntityValidationMessages.FIELD_ID_POSITIVE;
import static com.example.physical_exam.model.constant.EntityValidationMessages.RESULT_OF_EXECUTION_POSITIVE;
import static com.example.physical_exam.model.constant.EntityValidationMessages.YEAR_POSITIVE;

/**
 * Entity Class that represents results table in the physical_exam database
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "results")
public class Result extends BaseEntity {

    @NotNull(message = FIELD_NOT_NULL)
    @Positive(message = FIELD_ID_POSITIVE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Positive(message = YEAR_POSITIVE)
    @Min(value = 2000)
    @Column(name = "year_of_performance", length = 4)
    private Integer yearOfPerformance;

    @Positive(message = RESULT_OF_EXECUTION_POSITIVE)
    @Column(name = "running_time_in_seconds", length = 4)
    private Integer runningTimeInSeconds;

    @Positive(message = RESULT_OF_EXECUTION_POSITIVE)
    @Column(name = "crunches_count", length = 3)
    private Integer crunchesCount;

    @Positive(message = RESULT_OF_EXECUTION_POSITIVE)
    @Column(name = "push_ups_count", length = 3)
    private Integer pushUpsCount;

    @Positive(message = RESULT_OF_EXECUTION_POSITIVE)
    @Column(name = "jump_in_centimeters", length = 3)
    private Integer jumpInCentimeters;

    @Enumerated(EnumType.STRING)
    @NotNull(message = FIELD_NOT_NULL)
    @Column(name = "conclusion", length = 6)
    private Conclusion conclusion;
}
