package com.example.physical_exam;

import com.example.physical_exam.creator.ResultCreator;
import com.example.physical_exam.model.dto.response.ResultResponseDto;
import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.model.enumeration.Conclusion;
import com.example.physical_exam.model.enumeration.SortingOrder;
import com.example.physical_exam.repository.ResultRepository;
import com.example.physical_exam.service.impl.ResultServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ResultServiceTest {

    @InjectMocks
    private ResultServiceImpl resultService;

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private ModelMapper modelMapper;

    private ResultCreator resultCreator = new ResultCreator();

    @Test
    void whenFindAllResultsByYearAndConclusionNull_thenFoundAll() {
        Result successfulResult = resultCreator.createResultPassed();
        Result unsuccessfulResult = resultCreator.createResultFailed();
        List<Result> expectedResults = List.of(successfulResult, unsuccessfulResult);

        ResultResponseDto successfulResultDto = resultCreator.createResultResponseDtoPassed();
        ResultResponseDto unSuccessfulResultDto = resultCreator.createResultResponseDtoFailed();

        when(resultRepository.findAllOrderByYearOfPerformance()).thenReturn(expectedResults);
        when(modelMapper.map(successfulResult, ResultResponseDto.class)).thenReturn(successfulResultDto);
        when(modelMapper.map(unsuccessfulResult,ResultResponseDto.class)).thenReturn(unSuccessfulResultDto);

        List<ResultResponseDto> actualResults = resultService.findAllResultsByYearAndConclusion(null, null, SortingOrder.ASC);

        ResultResponseDto firstResult = actualResults.get(0);
        ResultResponseDto secondResult = actualResults.get(1);

        assertEquals(expectedResults.size(), actualResults.size(), "Results count must be " + expectedResults.size() +
                ", but actually it is " + actualResults.size());
        assertEquals(successfulResult.getConclusion(), firstResult.getConclusion(), "The conclusion must be " +
                successfulResult.getConclusion() + ", but actually it is " + firstResult.getConclusion());
        assertEquals(successfulResult.getCrunchesCount(), firstResult.getCrunchesCount());
        assertEquals(successfulResult.getJumpInCentimeters(), firstResult.getJumpInCentimeters());
        assertEquals(successfulResult.getPushUpsCount(), firstResult.getPushUpsCount());
        assertEquals(successfulResult.getRunningTimeInSeconds(), firstResult.getRunningTimeInSeconds());
        assertEquals(successfulResult.getYearOfPerformance(), firstResult.getYearOfPerformance());
        assertEquals(unsuccessfulResult.getConclusion(), secondResult.getConclusion());
        assertEquals(unsuccessfulResult.getRunningTimeInSeconds(), secondResult.getRunningTimeInSeconds());
        assertEquals(unsuccessfulResult.getPushUpsCount(), secondResult.getPushUpsCount());
        assertEquals(unsuccessfulResult.getYearOfPerformance(), secondResult.getYearOfPerformance());
    }

    @Test
    void whenFindAllResultsByYearAndConclusionNotNull_thenFoundAll() {
        Result successfulResult = resultCreator.createResultPassed();
        List<Result> expectedResults = List.of(successfulResult);

        Integer yearOfPerformance = successfulResult.getYearOfPerformance();
        Conclusion conclusion = successfulResult.getConclusion();

        ResultResponseDto successfulResultDto = resultCreator.createResultResponseDtoPassed();

        when(resultRepository.findAllByYearOfPerformanceAndConclusionOrderByYearOfPerformance(yearOfPerformance, conclusion))
                .thenReturn(expectedResults);
        when(modelMapper.map(successfulResult, ResultResponseDto.class)).thenReturn(successfulResultDto);

        List<ResultResponseDto> actualResults = resultService.findAllResultsByYearAndConclusion(yearOfPerformance, conclusion, SortingOrder.ASC);

        ResultResponseDto firstResult = actualResults.get(0);

        assertEquals(expectedResults.size(), actualResults.size(), "Results count must be " + expectedResults.size() +
                ", but actually it is " + actualResults.size());
        assertEquals(successfulResult.getConclusion(), firstResult.getConclusion(), "The conclusion must be " +
                successfulResult.getConclusion() + ", but actually it is " + firstResult.getConclusion());
        assertEquals(successfulResult.getCrunchesCount(), firstResult.getCrunchesCount());
        assertEquals(successfulResult.getJumpInCentimeters(), firstResult.getJumpInCentimeters());
        assertEquals(successfulResult.getPushUpsCount(), firstResult.getPushUpsCount());
        assertEquals(successfulResult.getRunningTimeInSeconds(), firstResult.getRunningTimeInSeconds());
        assertEquals(successfulResult.getYearOfPerformance(), firstResult.getYearOfPerformance());
    }

    @Test
    void whenFindAllResultsByYearNotNullConclusionNull_thenFound() {
        Result successfulResult = resultCreator.createResultPassed();
        List<Result> expectedResults = List.of(successfulResult);
        Integer yearOfPerformance = successfulResult.getYearOfPerformance();

        ResultResponseDto successfulResultDto = resultCreator.createResultResponseDtoPassed();

        when(resultRepository.findAllByYearOfPerformance(yearOfPerformance)).thenReturn(expectedResults);
        when(modelMapper.map(successfulResult, ResultResponseDto.class)).thenReturn(successfulResultDto);

        List<ResultResponseDto> actualResults = resultService.findAllResultsByYearAndConclusion(yearOfPerformance, null, SortingOrder.ASC);

        ResultResponseDto firstResult = actualResults.get(0);

        assertEquals(expectedResults.size(), actualResults.size(), "Results count must be " + expectedResults.size() +
                ", but actually it is " + actualResults.size());
        assertEquals(successfulResultDto.getConclusion(), firstResult.getConclusion(), "The conclusion must be " +
                successfulResultDto.getConclusion() + ", but actually it is " + firstResult.getConclusion());
        assertEquals(successfulResultDto.getCrunchesCount(), firstResult.getCrunchesCount());
        assertEquals(successfulResultDto.getJumpInCentimeters(), firstResult.getJumpInCentimeters());
        assertEquals(successfulResultDto.getPushUpsCount(), firstResult.getPushUpsCount());
        assertEquals(successfulResultDto.getRunningTimeInSeconds(), firstResult.getRunningTimeInSeconds());
        assertEquals(successfulResultDto.getYearOfPerformance(), firstResult.getYearOfPerformance());
    }

    @Test
    void whenFindAllResultsByYearNullConclusionNotNull_thenFound() {
        Result successfulResult = resultCreator.createResultPassed();
        List<Result> expectedResults = List.of(successfulResult);
        Conclusion conclusion = successfulResult.getConclusion();

        ResultResponseDto successfulResultDto = resultCreator.createResultResponseDtoPassed();

        when(resultRepository.findAllByConclusionOrderByYearOfPerformance(conclusion)).thenReturn(expectedResults);
        when(modelMapper.map(successfulResult, ResultResponseDto.class)).thenReturn(successfulResultDto);

        List<ResultResponseDto> actualResults = resultService.findAllResultsByYearAndConclusion(null, conclusion, SortingOrder.ASC);

        ResultResponseDto firstResult = actualResults.get(0);

        assertEquals(expectedResults.size(), actualResults.size(), "Results count must be " + expectedResults.size() +
                ", but actually it is " + actualResults.size());
        assertEquals(successfulResultDto.getConclusion(), firstResult.getConclusion(), "The conclusion must be " +
                successfulResultDto.getConclusion() + ", but actually it is " + firstResult.getConclusion());
        assertEquals(successfulResultDto.getCrunchesCount(), firstResult.getCrunchesCount());
        assertEquals(successfulResultDto.getJumpInCentimeters(), firstResult.getJumpInCentimeters());
        assertEquals(successfulResultDto.getPushUpsCount(), firstResult.getPushUpsCount());
        assertEquals(successfulResultDto.getRunningTimeInSeconds(), firstResult.getRunningTimeInSeconds());
        assertEquals(successfulResultDto.getYearOfPerformance(), firstResult.getYearOfPerformance());
    }

    @Test
    void whenFindAllResultsByYearNullConclusionNotNullOrderDesc_thenFound() {
        Result successfulResult = resultCreator.createResultPassed();
        List<Result> expectedResults = List.of(successfulResult);
        Conclusion conclusion = successfulResult.getConclusion();

        ResultResponseDto successfulResultDto = resultCreator.createResultResponseDtoPassed();

        when(resultRepository.findAllByConclusionOrderByYearOfPerformance(conclusion)).thenReturn(expectedResults);
        when(modelMapper.map(successfulResult, ResultResponseDto.class)).thenReturn(successfulResultDto);

        List<ResultResponseDto> actualResults = resultService.findAllResultsByYearAndConclusion(null, conclusion, SortingOrder.DESC);

        ResultResponseDto firstResult = actualResults.get(0);

        assertEquals(expectedResults.size(), actualResults.size(), "Results count must be " + expectedResults.size() +
                ", but actually it is " + actualResults.size());
        assertEquals(successfulResultDto.getConclusion(), firstResult.getConclusion(), "The conclusion must be " +
                successfulResultDto.getConclusion() + ", but actually it is " + firstResult.getConclusion());
        assertEquals(successfulResultDto.getCrunchesCount(), firstResult.getCrunchesCount());
        assertEquals(successfulResultDto.getJumpInCentimeters(), firstResult.getJumpInCentimeters());
        assertEquals(successfulResultDto.getPushUpsCount(), firstResult.getPushUpsCount());
        assertEquals(successfulResultDto.getRunningTimeInSeconds(), firstResult.getRunningTimeInSeconds());
        assertEquals(successfulResultDto.getYearOfPerformance(), firstResult.getYearOfPerformance());
    }

    @Test
    void whenSaveResult_thenSavedSuccessfully() {

    }
}
