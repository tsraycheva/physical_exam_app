package com.example.physical_exam.service.impl;

import com.example.physical_exam.model.dto.request.ResultCreationRequestDto;
import com.example.physical_exam.model.dto.response.ResultResponseDto;
import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.model.enumeration.Conclusion;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.repository.ResultRepository;
import com.example.physical_exam.service.EmployeeService;
import com.example.physical_exam.service.ExerciseService;
import com.example.physical_exam.service.ResultService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    private static final String CRUNCHES_EXERCISE = "crunches";
    private static final String JUMP_EXERCISE = "long jump";
    private static final String PUSH_UPS_EXERCISE = "push-ups";
    private static final String RUNNING_EXERCISE = "800 meters run";

    private final ResultRepository resultRepository;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final ExerciseService exerciseService;

    public ResultServiceImpl(ResultRepository resultRepository, ModelMapper modelMapper, EmployeeService employeeService, ExerciseService exerciseService) {
        this.resultRepository = resultRepository;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
        this.exerciseService = exerciseService;
    }

    @Override
    public ResultResponseDto saveResult(ResultCreationRequestDto requestDto) {
        Conclusion conclusion = makeConclusion(requestDto);
        Long employeeId = requestDto.getEmployeeId();
        Result resultToSave = modelMapper.map(requestDto, Result.class);
        resultToSave.setConclusion(conclusion);
        Employee employee = modelMapper.map(employeeService.findEmployeeById(employeeId), Employee.class);
        resultToSave.setEmployee(employee);
        Result savedResult = resultRepository.saveAndFlush(resultToSave);
        ResultResponseDto savedResultResponse = modelMapper.map(savedResult, ResultResponseDto.class);

        return savedResultResponse;
    }

    @Override
    public List<ResultResponseDto> findResultsForSpecifiedYear(Integer year) {
        List<Result> results = resultRepository.findAllByYearOfPerformance(year);

        return results
                .stream()
                .map(r -> modelMapper.map(r, ResultResponseDto.class))
                .collect(Collectors.toList());
    }

    private Conclusion makeConclusion(ResultCreationRequestDto resultCreationRequestDto) {
        Long employeeId = resultCreationRequestDto.getEmployeeId();
        Gender gender = employeeService.findEmployeeById(employeeId).getGender();
        Integer crunchesCount = resultCreationRequestDto.getCrunchesCount();
        Integer jumpInCentimeters = resultCreationRequestDto.getJumpInCentimeters();
        Integer pushUpsCount = resultCreationRequestDto.getPushUpsCount();
        Integer runningTimeInSeconds = resultCreationRequestDto.getRunningTimeInSeconds();

        Conclusion conclusion;

        if (doPass(gender, CRUNCHES_EXERCISE, crunchesCount)
                        && doPass(gender, JUMP_EXERCISE, jumpInCentimeters)
                        && doPass(gender, PUSH_UPS_EXERCISE, pushUpsCount)
                        && doPass(gender, RUNNING_EXERCISE, runningTimeInSeconds)) {

            conclusion = Conclusion.PASSED;
        } else {
            conclusion = Conclusion.FAILED;
        }

        return conclusion;
    }

    private boolean doPass(Gender gender, String name, Integer achievement) {
        Exercise exercise = exerciseService.findExerciseByGenderAndName(gender, name);
        Integer requirement = exercise.getRequirement();

        if (name.equals(RUNNING_EXERCISE)) {
            return requirement > achievement;
        }

        return requirement < achievement;
    }
}
