package com.example.physical_exam.web;

import com.example.physical_exam.model.dto.request.ResultCreationRequestDto;
import com.example.physical_exam.model.dto.response.ResultResponseDto;
import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.service.ResultService;
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

    @PostMapping
    public ResultResponseDto saveResult(@RequestBody @Valid ResultCreationRequestDto requestDto) {
        ResultResponseDto savedResult = resultService.saveResult(requestDto);

        return savedResult;
    }

    @GetMapping
    public List<ResultResponseDto> getAllResultsByYear(@RequestParam Integer year) {
        List<ResultResponseDto> resultsForSpecifiedYear = resultService.findResultsForSpecifiedYear(year);

        return resultsForSpecifiedYear;
    }
}
