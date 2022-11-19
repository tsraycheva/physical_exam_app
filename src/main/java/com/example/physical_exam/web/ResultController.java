package com.example.physical_exam.web;

import com.example.physical_exam.exception.CanNotPerformOperationException;
import com.example.physical_exam.model.dto.request.ResultCreationRequestDto;
import com.example.physical_exam.model.dto.response.ResultCreationResponseDto;
import com.example.physical_exam.model.dto.response.ResultResponseDto;
import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Result Controller with endpoints related to {@link Result} Objects
 */
@RestController
@RequestMapping("/api/v1/physical_exam/result")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    /**
     * Endpoint for making a request for saving a result from exam of an employee
     *
     * @param requestDto {@link ResultCreationRequestDto} with needed information from exam
     * @return {@link ResultCreationResponseDto} with stored Result
     */
    @Operation(
            tags  = "result",
            summary = "Method for saving a result from exam of an employee",
            operationId = "saveResult",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data transfer object to save a result.",
                    content = @Content(schema = @Schema(implementation = ResultCreationRequestDto.class))),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResultCreationResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Successful operation!"),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = CanNotPerformOperationException.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Operation failed!")})
    @PostMapping
    public ResultCreationResponseDto saveResult(@RequestBody @Valid ResultCreationRequestDto requestDto) {
        ResultCreationResponseDto savedResult = resultService.saveResult(requestDto);

        return savedResult;
    }

    /**
     * Endpoint that makes request to find all results from a specified year of performance.
     *
     * @param year of performance
     * @return list {@link ResultResponseDto} with all results from a specified year
     * or empty list if no results are found
     */
    @Operation(
            tags  = "result",
            summary = "Method for searching all results by year of performance.",
            operationId = "findAllResultsByYear",
            parameters = {
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "year",
                            description = "year of performance of the searched results",
                            example = "2020")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResultResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Successful operation!"),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = CanNotPerformOperationException.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Operation failed!")})
    @GetMapping
    public List<ResultResponseDto> getAllResultsByYear(@RequestParam Integer year) {
        List<ResultResponseDto> resultsForSpecifiedYear = resultService.findResultsForSpecifiedYear(year);

        return resultsForSpecifiedYear;
    }
}
