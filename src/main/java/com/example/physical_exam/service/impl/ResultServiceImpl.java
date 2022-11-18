package com.example.physical_exam.service.impl;

import com.example.physical_exam.model.dto.request.ResultCreationRequestDto;
import com.example.physical_exam.model.dto.response.EmployeeResponseDto;
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
        Result resultToSave = modelMapper.map(requestDto, Result.class);

        Conclusion conclusion = makeConclusion(requestDto);
        resultToSave.setConclusion(conclusion);

        Long employeeId = requestDto.getEmployeeId();
        EmployeeResponseDto employeeResponse = employeeService.findEmployeeById(employeeId);
        Employee employee = modelMapper.map(employeeResponse, Employee.class);
        resultToSave.setEmployees(List.of(employee));

        Result savedResult = resultRepository.save(resultToSave);
        ResultResponseDto savedResultResponse = modelMapper.map(savedResult, ResultResponseDto.class);

        String employeeFirstName = employee.getFirstName();
        String employeeLastName = employee.getLastName();
        savedResultResponse.setEmployeeNames(employeeFirstName.concat(" ").concat(employeeLastName));

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

    /**
     * Method that checks if all the exercises are passed successfully and makes
     * conclusion on the basis of requirements for the specified exercise and the
     * gender of the employee
     *
     * @param resultCreationRequestDto {@link ResultCreationRequestDto} with
     * all the data from the exam
     * @return {@link Conclusion} of the exam
     */
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

    /**
     * Method that accepts the gender of the employee, his/her achievement and
     *  the name of the exercise to check if he/she has passed successfully or failed
     *
     * @param gender {@link Gender} of the {@link Employee}
     * @param name of {@link Exercise}
     * @param achievement of the {@link Employee} doing the exercise
     * @return boolean true if the employee has passed or false if failed
     */
    private boolean doPass(Gender gender, String name, Integer achievement) {
        Exercise exercise = exerciseService.findExerciseByGenderAndName(gender, name);
        Integer requirement = exercise.getRequirement();

        if (name.equals(RUNNING_EXERCISE)) {
            return requirement > achievement;
        }

        return requirement < achievement;
    }
}
