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
     * Method that searches for all results by year
     *
     * @param year of performance Integer
     * @param order of appearance {@link SortingOrder}
     * @return list of {@link ResultResponseDto} with all results by specified year
     */
    List<ResultResponseDto> findResultsForSpecifiedYear(Integer year, SortingOrder order);

    /**
     * Method that searches for all results by {@link Conclusion}
     *
     * @param conclusion of performance {@link Conclusion}
     * @param order of appearance {@link SortingOrder}
     * @return list of {@link ResultResponseDto} with all results by {@link Conclusion}
     */
    List<ResultResponseDto> findAllResultsByConclusion(Conclusion conclusion, SortingOrder order);

    /**
     * Method that searches for all results by year of performance and {@link Conclusion}
     *
     * @param year of performance Integer
     * @param conclusion of performance {@link Conclusion}
     * @param order of appearance {@link SortingOrder}
     * @return list of {@link ResultResponseDto} with all results by specified year and conclusion
     */
    List<ResultResponseDto> findResultsByYearAndByConclusion(Integer year, Conclusion conclusion, SortingOrder order);

    /**
     * Method that searches for all results
     *
     * @return list of {@link ResultResponseDto} with all results
     */
    List<ResultResponseDto> findAllResults();

}
