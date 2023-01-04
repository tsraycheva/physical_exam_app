package com.example.physical_exam.creator;

import com.example.physical_exam.model.dto.response.ResultResponseDto;
import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.model.enumeration.Conclusion;

import java.util.List;

/**
 * Class that is used to create {@link Result} and {@link ResultResponseDto} for test purposes
 */
public class ResultCreator {

    private EmployeeCreator employeeCreator = new EmployeeCreator();

    /**
     * Method that creates {@link Result} for list of {@link Employee} for test purposes with
     * Conclusion PASSED
     *
     * @return {@link Result} with {@link Conclusion} PASSED
     */
    public Result createResultPassed() {
        Employee employee = employeeCreator.createMalePeshoEmployee();
        return new Result(
                List.of(employee),
                2020,
                230,
                44,
                45,
                210,
                Conclusion.PASSED);
    }

    /**
     * Method that creates {@link Result} for list of {@link Employee} for test purposes with
     * Conclusion FAILED
     *
     * @return {@link Result} with {@link Conclusion} FAILED
     */
    public Result createResultFailed() {
        Employee employee = employeeCreator.createMalePeshoEmployee();
        return new Result(
                List.of(employee),
                2021,
                420,
                41,
                40,
                210,
                Conclusion.FAILED);
    }

    /**
     * Method that creates {@link ResultResponseDto} for test purposes with Conclusion PASSED
     *
     * @return {@link ResultResponseDto} with {@link Conclusion} PASSED
     */
    public ResultResponseDto createResultResponseDtoPassed() {
        return new ResultResponseDto(
                2020,
                230,
                44,
                45,
                210,
                Conclusion.PASSED);
    }

    /**
     * Method that creates {@link ResultResponseDto} for test purposes with Conclusion FAILED
     *
     * @return {@link ResultResponseDto} with {@link Conclusion} FAILED
     */
    public ResultResponseDto createResultResponseDtoFailed() {
        return new ResultResponseDto(
                2021,
                420,
                41,
                40,
                210,
                Conclusion.FAILED);
    }
}
