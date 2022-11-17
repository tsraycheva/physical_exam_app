package com.example.physical_exam.service;

import com.example.physical_exam.model.dto.request.ResultCreationRequestDto;
import com.example.physical_exam.model.dto.response.ResultResponseDto;
import com.example.physical_exam.model.entity.Result;

import java.util.List;

/**
 * Class that holds all the business logic related to {@link Result} Objects
 */
public interface ResultService {

    /**
     * Method that serves for saving a {@link Result} from exam of an Employee
     *
     * @param requestDto {@link ResultCreationRequestDto}
     * @return {@link ResultResponseDto} with information and conclusion
     */
    ResultResponseDto saveResult(ResultCreationRequestDto requestDto);

    /**
     * Method that searches for {@link Result} by year of performance
     *
     * @param year of performance Integer
     * @return list of Result Entities wrapped into {@link ResultResponseDto}
     */
    List<ResultResponseDto> findResultsForSpecifiedYear(Integer year);
}
