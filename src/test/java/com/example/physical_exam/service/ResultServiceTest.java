package com.example.physical_exam.service;

import com.example.physical_exam.creator.EmployeeCreator;
import com.example.physical_exam.creator.ExerciseCreator;
import com.example.physical_exam.creator.ResultCreator;
import com.example.physical_exam.model.dto.request.ResultCreationRequestDto;
import com.example.physical_exam.model.dto.response.EmployeeResponseDto;
import com.example.physical_exam.model.dto.response.ResultCreationResponseDto;
import com.example.physical_exam.model.dto.response.ResultResponseDto;
import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.model.enumeration.Conclusion;
import com.example.physical_exam.model.enumeration.Gender;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResultServiceTest {

    @InjectMocks
    private ResultServiceImpl resultService;

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ExerciseService exerciseService;

    @Mock
    private ModelMapper modelMapper;

    private ResultCreator resultCreator = new ResultCreator();

    private EmployeeCreator employeeCreator = new EmployeeCreator();

    private ExerciseCreator exerciseCreator = new ExerciseCreator();

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
    void whenSaveResultPassed_thenSavedSuccessfully() {
        ResultCreationRequestDto resultCreationRequestDto = resultCreator.createResultCreationRequestDtoPassed();
        Result resultToSave = resultCreator.createResultPassed();
        ResultCreationResponseDto resultCreationResponseDto = resultCreator.createResultCreationResponseDtoPassed();

        EmployeeResponseDto employeeDto = employeeCreator.createMalePeshoEmployeeResponseDto();
        Employee employee = employeeCreator.createMalePeshoEmployee();
        Long employeeId = 1L;
        Gender gender = employee.getGender();

        Exercise exerciseJump = exerciseCreator.createExerciseJumpFemale();
        Exercise exerciseCrunches = exerciseCreator.createExerciseCrunchesFemale();
        Exercise exerciseRunning = exerciseCreator.createExerciseRunningFemale();
        Exercise exercisePushUps = exerciseCreator.createExercisePushUpsFemale();

        when(modelMapper.map(resultCreationRequestDto, Result.class)).thenReturn(resultToSave);
        when(modelMapper.map(resultToSave, ResultCreationResponseDto.class)).thenReturn(resultCreationResponseDto);
        when(modelMapper.map(employeeDto, Employee.class)).thenReturn(employee);
        when(employeeService.findEmployeeById(employeeId)).thenReturn(employeeDto);
        when(resultRepository.save(any(Result.class))).thenReturn(resultToSave);
        when(exerciseService.findExerciseByGenderAndName(gender, exerciseJump.getName())).thenReturn(exerciseJump);
        when(exerciseService.findExerciseByGenderAndName(gender, exerciseCrunches.getName())).thenReturn(exerciseCrunches);
        when(exerciseService.findExerciseByGenderAndName(gender, exerciseRunning.getName())).thenReturn(exerciseRunning);
        when(exerciseService.findExerciseByGenderAndName(gender, exercisePushUps.getName())).thenReturn(exercisePushUps);

        ResultCreationResponseDto actualResultResponse = resultService.saveResult(resultCreationRequestDto);

        assertEquals(resultCreationRequestDto.getCrunchesCount(), actualResultResponse.getCrunchesCount());
    }

    @Test
    void whenSaveResultFailed_thenSavedSuccessfully() {
        ResultCreationRequestDto resultCreationRequestDto = resultCreator.createResultCreationRequestDtoFailed();
        Result resultToSave = resultCreator.createResultFailed();
        ResultCreationResponseDto resultCreationResponseDto = resultCreator.createResultCreationResponseDtoFailed();

        EmployeeResponseDto employeeDto = employeeCreator.createMalePeshoEmployeeResponseDto();
        Employee employee = employeeCreator.createMalePeshoEmployee();
        Long employeeId = 1L;
        Gender gender = employee.getGender();

        Exercise exerciseJump = exerciseCreator.createExerciseJumpFemale();
        Exercise exerciseCrunches = exerciseCreator.createExerciseCrunchesFemale();
        Exercise exerciseRunning = exerciseCreator.createExerciseRunningFemale();
        Exercise exercisePushUps = exerciseCreator.createExercisePushUpsFemale();

        when(modelMapper.map(resultCreationRequestDto, Result.class)).thenReturn(resultToSave);
        when(modelMapper.map(resultToSave, ResultCreationResponseDto.class)).thenReturn(resultCreationResponseDto);
        when(modelMapper.map(employeeDto, Employee.class)).thenReturn(employee);
        when(employeeService.findEmployeeById(employeeId)).thenReturn(employeeDto);
        when(resultRepository.save(any(Result.class))).thenReturn(resultToSave);
        when(exerciseService.findExerciseByGenderAndName(gender, exerciseJump.getName())).thenReturn(exerciseJump);
        when(exerciseService.findExerciseByGenderAndName(gender, exerciseCrunches.getName())).thenReturn(exerciseCrunches);
        when(exerciseService.findExerciseByGenderAndName(gender, exerciseRunning.getName())).thenReturn(exerciseRunning);
        when(exerciseService.findExerciseByGenderAndName(gender, exercisePushUps.getName())).thenReturn(exercisePushUps);

        ResultCreationResponseDto actualResultResponse = resultService.saveResult(resultCreationRequestDto);

        assertEquals(resultCreationRequestDto.getCrunchesCount(), actualResultResponse.getCrunchesCount());
    }
}
