package com.example.physical_exam.service;

import com.example.physical_exam.model.dto.request.ResultCreationRequestDto;
import com.example.physical_exam.model.dto.response.ResultCreationResponseDto;
import com.example.physical_exam.model.dto.response.ResultResponseDto;
import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.model.enumeration.Conclusion;
import com.example.physical_exam.model.enumeration.SortingOrder;

import java.util.List;

/**
 * Class that holds all the business logic related to {@link Result} Objects
 */
public interface ResultService {

    /**
     * Method that serves for saving a {@link Result} from exam of an Employee
     *
     * @param requestDto {@link ResultCreationRequestDto}
     * @return {@link ResultCreationResponseDto} with information and conclusion
     */
    ResultCreationResponseDto saveResult(ResultCreationRequestDto requestDto);

    /**
     * Method that checks which parameters are passed and finds all results by given criteria
     *
     * @param year of performance - Integer
     * @param conclusion of performance {@link Conclusion}
     * @param order {@link SortingOrder}
     * @return list with all results wrapped into {@link ResultResponseDto}
     */
    List<ResultResponseDto> findAllResultsByYearAndConclusion(Integer year, Conclusion conclusion, SortingOrder order);
}
