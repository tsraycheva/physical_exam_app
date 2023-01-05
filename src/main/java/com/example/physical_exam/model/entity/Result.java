package com.example.physical_exam.model.entity;

import com.example.physical_exam.model.enumeration.Conclusion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static com.example.physical_exam.model.constant.ValidationMessages.FIELD_NOT_NULL;
import static com.example.physical_exam.model.constant.ValidationMessages.RESULT_OF_EXECUTION_POSITIVE;
import static com.example.physical_exam.model.constant.ValidationMessages.YEAR_POSITIVE;

/**
 * Entity Class that represents results table in the physical_exam database
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "results")
public class Result extends BaseEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employees_results",
            joinColumns = @JoinColumn(name = "result_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private List<Employee> employees;

    @Positive(message = YEAR_POSITIVE)
    @Min(value = 2000)
    @Column(name = "year_of_performance", length = 4)
    private Integer yearOfPerformance;

    @Min(value = 0, message = RESULT_OF_EXECUTION_POSITIVE)
    @Column(name = "running_time_in_seconds", length = 4)
    private Integer runningTimeInSeconds;

    @Min(value = 0, message = RESULT_OF_EXECUTION_POSITIVE)
    @Column(name = "crunches_count", length = 3)
    private Integer crunchesCount;

    @Min(value = 0, message = RESULT_OF_EXECUTION_POSITIVE)
    @Column(name = "push_ups_count", length = 3)
    private Integer pushUpsCount;

    @Min(value = 0, message = RESULT_OF_EXECUTION_POSITIVE)
    @Column(name = "jump_in_centimeters", length = 3)
    private Integer jumpInCentimeters;

    @Enumerated(EnumType.STRING)
    @NotNull(message = FIELD_NOT_NULL)
    @Column(name = "conclusion", length = 6)
    private Conclusion conclusion;
}
