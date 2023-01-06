package com.example.physical_exam.service.impl;

import com.example.physical_exam.model.dto.request.ResultCreationRequestDto;
import com.example.physical_exam.model.dto.response.EmployeeResponseDto;
import com.example.physical_exam.model.dto.response.ResultCreationResponseDto;
import com.example.physical_exam.model.dto.response.ResultResponseDto;
import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.model.enumeration.Conclusion;
import com.example.physical_exam.model.enumeration.ExerciseEnum;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.model.enumeration.SortingOrder;
import com.example.physical_exam.repository.ResultRepository;
import com.example.physical_exam.service.EmployeeService;
import com.example.physical_exam.service.ExerciseService;
import com.example.physical_exam.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.physical_exam.model.enumeration.ExerciseEnum.RUNNING;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final ExerciseService exerciseService;

    @Override
    public ResultCreationResponseDto saveResult(ResultCreationRequestDto requestDto) {
        Result resultToSave = modelMapper.map(requestDto, Result.class);

        Conclusion conclusion = makeConclusion(requestDto);
        resultToSave.setConclusion(conclusion);

        Long employeeId = requestDto.getEmployeeId();
        EmployeeResponseDto employeeResponse = employeeService.findEmployeeById(employeeId);
        Employee employee = modelMapper.map(employeeResponse, Employee.class);
        resultToSave.setEmployees(List.of(employee));

        Result savedResult = resultRepository.save(resultToSave);
        ResultCreationResponseDto savedResultResponse = modelMapper.map(savedResult, ResultCreationResponseDto.class);

        String employeeFirstName = employee.getFirstName();
        String employeeLastName = employee.getLastName();
        savedResultResponse.setEmployeeNames(employeeFirstName.concat(" ").concat(employeeLastName));

        log.info("Result with id {} for employee with id {} saved.", savedResult.getId(), employee.getId());

        return savedResultResponse;
    }

    @Override
    public List<ResultResponseDto> findAllResultsByYearAndConclusion(Integer year, Conclusion conclusion, SortingOrder order) {

        List<ResultResponseDto> results;

        if (conclusion == null && year != null) {
            results = findResultsForSpecifiedYear(year, order);
        } else if (conclusion != null && year == null) {
            results = findResultsByConclusion(conclusion, order);
        } else if (conclusion == null && year == null) {
            results = findAllResults();
        } else {
            results = findResultsByYearAndByConclusion(year, conclusion, order);
        }

        return results;
    }

    /**
     * Method that searches for all results by {@link Conclusion}
     *
     * @param conclusion of performance {@link Conclusion}
     * @param order      of appearance {@link SortingOrder} by year of performance
     * @return list of {@link ResultResponseDto} with all results by {@link Conclusion}
     */
    private List<ResultResponseDto> findResultsByConclusion(Conclusion conclusion, SortingOrder order) {
        List<Result> results = resultRepository.findAllByConclusionOrderByYearOfPerformance(conclusion);

        if (order == SortingOrder.DESC) {
            Collections.reverse(results);
        }

        log.info("When searching for all results by conclusion {}, found {} results.", conclusion, results.size());

        return results
                .stream()
                .map(r -> modelMapper.map(r, ResultResponseDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Method that searches for all results by year of performance and {@link Conclusion}
     *
     * @param year       of performance Integer
     * @param conclusion of performance {@link Conclusion}
     * @param order      of appearance {@link SortingOrder} by result id
     * @return list of {@link ResultResponseDto} with all results by specified year and conclusion
     */
    private List<ResultResponseDto> findResultsByYearAndByConclusion(Integer year, Conclusion conclusion, SortingOrder order) {
        List<Result> results = resultRepository.findAllByYearOfPerformanceAndConclusionOrderById(year, conclusion);

        if (order == SortingOrder.DESC) {
            Collections.reverse(results);
        }

        log.info("When searching for all results from exam performed in {} year with {} conclusion, found {} results.",
                year, conclusion, results.size());

        return results
                .stream()
                .map(r -> modelMapper.map(r, ResultResponseDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Method that searches for all results and returns a list with found results ordered by year of performance
     *
     * @return list of {@link ResultResponseDto} with all results
     */
    private List<ResultResponseDto> findAllResults() {
        List<Result> results = resultRepository.findAllOrderByYearOfPerformance();

        log.info("When searching for all results, found {} results.", results.size());

        return results
                .stream()
                .map(r -> modelMapper.map(r, ResultResponseDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Method that searches for all results by year
     *
     * @param year  of performance Integer
     * @param order of appearance {@link SortingOrder} ordered by result id
     * @return list of {@link ResultResponseDto} with all results by specified year
     */
    private List<ResultResponseDto> findResultsForSpecifiedYear(Integer year, SortingOrder order) {
        List<Result> results = resultRepository.findAllByYearOfPerformanceOrderById(year);

        if (order == SortingOrder.DESC) {
            Collections.reverse(results);
        }

        log.info("When searching for all results from exam performed in {} year, found {} results.", year, results.size());

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
     *                                 all the data from the exam
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

        if (doPass(gender, ExerciseEnum.CRUNCHES, crunchesCount)
                && doPass(gender, ExerciseEnum.LONG_JUMP, jumpInCentimeters)
                && doPass(gender, ExerciseEnum.PUSH_UPS, pushUpsCount)
                && doPass(gender, RUNNING, runningTimeInSeconds)) {

            conclusion = Conclusion.PASSED;
        } else {
            conclusion = Conclusion.FAILED;
        }

        return conclusion;
    }

    /**
     * Method that accepts the gender of the employee, his/her achievement and
     * the name of the exercise to check if he/she has passed successfully or failed
     *
     * @param gender      {@link Gender} of the {@link Employee}
     * @param name        of {@link Exercise} - Object of type {@link ExerciseEnum}
     * @param achievement of the {@link Employee} doing the exercise
     * @return boolean true if the employee has passed or false if failed
     */
    private boolean doPass(Gender gender, ExerciseEnum name, Integer achievement) {
        Exercise exercise = exerciseService.findExerciseByGenderAndName(gender, name);
        Integer requirement = exercise.getRequirement();

        if (name.equals(ExerciseEnum.RUNNING)) {
            return requirement > achievement;
        }

        return requirement < achievement;
    }
}
