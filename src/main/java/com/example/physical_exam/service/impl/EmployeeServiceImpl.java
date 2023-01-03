package com.example.physical_exam.service.impl;

import com.example.physical_exam.exception.ResourceNotFoundException;
import com.example.physical_exam.model.dto.request.EmployeeCreationRequestDto;
import com.example.physical_exam.model.dto.response.EmployeeResponseDto;
import com.example.physical_exam.model.dto.response.EmployeeResultsResponseDto;
import com.example.physical_exam.model.dto.response.ResultResponseDto;
import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.model.enumeration.Conclusion;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.repository.EmployeeRepository;
import com.example.physical_exam.repository.ResultRepository;
import com.example.physical_exam.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final ResultRepository resultRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ModelMapper modelMapper,
                               ResultRepository resultRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;

        this.resultRepository = resultRepository;
    }

    @Override
    @Cacheable(value = "employees", key = "#id")
    public EmployeeResponseDto findEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            log.error("When searching for an employee with id {}, not found!", id);

            throw new ResourceNotFoundException("Not found employee with id " + id);
        }

        EmployeeResponseDto employeeResponse = modelMapper.map(employee, EmployeeResponseDto.class);

        log.info("When searching for employee with id {} found: {}.", id, employee.toString());

        return employeeResponse;
    }

    @Override
    @Cacheable(value = "employees", key = "#identityNumber")
    public EmployeeResultsResponseDto findEmployeeAndResultsByIdentityNumber(Integer identityNumber) {
        Employee employee = employeeRepository
                .findEmployeeByIdentificationNumber(identityNumber)
                .orElse(null);

        if (employee == null) {
            log.error("When searching for an employee with identificationNumber {}, not found!", identityNumber);

            throw new ResourceNotFoundException("Not found employee with identificationNumber " + identityNumber);
        }

        log.info("When searching for employee with identificationNumber {}, found: {}", identityNumber, employee.toString());

        EmployeeResultsResponseDto employeeResponse = getEmployeeWithNamesResponseDto(employee);

        List<Result> results = resultRepository.findResultByEmployeesId(employee.getId());

        return getEmployeeResultsResponseDto(employeeResponse, results);
    }

    @Override
    @Cacheable(value = "employees")
    public List<EmployeeResponseDto> findAllEmployees() {

        List<Employee> allEmployees = employeeRepository.findAll();
        List<EmployeeResponseDto> allEmployeesResponse =
                allEmployees
                        .stream()
                        .map(e -> modelMapper.map(e, EmployeeResponseDto.class))
                        .collect(Collectors.toList());

        log.info("When searching for all employees, there are {} found.", allEmployeesResponse.size());

        return allEmployeesResponse;
    }

    @Override
    @Cacheable(value = "employees", key = "#gender")
    public List<EmployeeResponseDto> findAllByGender(Gender gender) {
        List<Employee> employees = employeeRepository.findAllByGenderOrderByFirstName(gender);
        List<EmployeeResponseDto> allEmployeesByGender =
                employees
                        .stream()
                        .map(e -> modelMapper.map(e, EmployeeResponseDto.class))
                        .collect(Collectors.toList());

        log.info("When searching for all employees of {} gender, there are {} found.", gender.toString(),
                allEmployeesByGender.size());

        return allEmployeesByGender;
    }

    @Override
    @CacheEvict(value = {"employees"}, allEntries = true)
    public EmployeeResponseDto saveEmployee(EmployeeCreationRequestDto employeeRequestDto) {
        Employee employeeToSave = modelMapper.map(employeeRequestDto, Employee.class);
        Employee employeeEntity = employeeRepository.save(employeeToSave);
        EmployeeResponseDto savedEmployee = modelMapper.map(employeeEntity, EmployeeResponseDto.class);

        log.info("Employee with id {} successfully saved in DB!", employeeEntity.getId());

        return savedEmployee;
    }

    @Override
    public List<EmployeeResultsResponseDto> findAllEmployeesResults(Conclusion conclusion, Integer year) {
        List<Employee> foundEmployees = employeeRepository.findAll();
        List<EmployeeResultsResponseDto> foundEmployeesResponse;

        if (conclusion != null && year == null) {
            foundEmployeesResponse = findAllEmployeesResultsByConclusion(conclusion, foundEmployees);
        } else if (conclusion == null && year != null) {
            foundEmployeesResponse = findAllEmployeesResultsByYear(year, foundEmployees);
        } else if (conclusion != null && year != null) {
            foundEmployeesResponse = findAllEmployeesResultsByConclusionAndYear(conclusion, year, foundEmployees);
        } else {
            foundEmployeesResponse = findAllEmployeesWithResults(foundEmployees);
        }

        log.info("When searching for all employees with results by conclusion - {} and year - {}, found {} employees.",
                conclusion, year, foundEmployeesResponse.size());

        return foundEmployeesResponse;
    }

    /**
     * Method that obtains all employees that have been on exam and have at least one result
     *
     * @param foundEmployees list with all employees in database
     * @return list of {@link EmployeeResultsResponseDto} with data of all employees and their results
     */
    private List<EmployeeResultsResponseDto> findAllEmployeesWithResults(List<Employee> foundEmployees) {
        List<EmployeeResultsResponseDto> employees =
                foundEmployees
                        .stream()
                        .map(e -> {
                            EmployeeResultsResponseDto responseEmployee = getEmployeeWithNamesResponseDto(e);

                            List<Result> result = resultRepository.findResultByEmployeesId(e.getId());

                            return getEmployeeResultsResponseDto(responseEmployee, result);
                        })
                        .filter(employeeResultsResponseDto -> employeeResultsResponseDto.getResultResponseDto().size() != 0)
                        .collect(Collectors.toList());

        return employees;
    }

    /**
     * Method that obtains all employees with their results by {@link Conclusion}
     * and filters them so that only employees with results are in the list
     *
     * @param conclusion     {@link Conclusion} PASSED or FAILED
     * @param foundEmployees list with all employees in database
     * @return list of {@link EmployeeResultsResponseDto}
     */
    private List<EmployeeResultsResponseDto> findAllEmployeesResultsByConclusion(Conclusion conclusion,
                                                                                 List<Employee> foundEmployees) {

        List<EmployeeResultsResponseDto> employees =
                foundEmployees
                        .stream()
                        .map(e -> {
                            EmployeeResultsResponseDto responseEmployee = getEmployeeWithNamesResponseDto(e);

                            List<Result> result = resultRepository
                                    .findResultByEmployeesIdAndConclusion(e.getId(), conclusion);

                            return getEmployeeResultsResponseDto(responseEmployee, result);
                        })
                        .filter(employeeResultsResponseDto -> employeeResultsResponseDto.getResultResponseDto().size() != 0)
                        .collect(Collectors.toList());

        return employees;
    }

    /**
     * Method that obtains all employees with their results by year of performance
     * and filters them so that only employees with results are in the list
     *
     * @param year           of performance Integer
     * @param foundEmployees list with all employees in database
     * @return list of {@link EmployeeResultsResponseDto}
     */
    private List<EmployeeResultsResponseDto> findAllEmployeesResultsByYear(Integer year,
                                                                           List<Employee> foundEmployees) {
        List<EmployeeResultsResponseDto> employees =
                foundEmployees
                        .stream()
                        .map(e -> {
                            EmployeeResultsResponseDto responseEmployee = getEmployeeWithNamesResponseDto(e);

                            List<Result> result = resultRepository
                                    .findResultByEmployeesIdAndYearOfPerformance(e.getId(), year);

                            return getEmployeeResultsResponseDto(responseEmployee, result);
                        })
                        .filter(employeeResultsResponseDto -> employeeResultsResponseDto.getResultResponseDto().size() != 0)
                        .collect(Collectors.toList());

        return employees;
    }

    /**
     * Method that obtains all employees with their results by {@link Conclusion} and year of performance
     * and filters them so that only employees with results are in the list
     *
     * @param conclusion     {@link Conclusion} PASSED or FAILED
     * @param year           year of performance Integer
     * @param foundEmployees list with all employees in database
     * @return list of {@link EmployeeResultsResponseDto}
     */
    private List<EmployeeResultsResponseDto> findAllEmployeesResultsByConclusionAndYear(Conclusion conclusion,
                                                                                        Integer year,
                                                                                        List<Employee> foundEmployees) {
        List<EmployeeResultsResponseDto> employees =
                foundEmployees
                        .stream()
                        .map(e -> {
                            EmployeeResultsResponseDto responseEmployee = getEmployeeWithNamesResponseDto(e);

                            List<Result> result = resultRepository
                                    .findResultByEmployeesIdAndConclusionAndYearOfPerformance(e.getId(), conclusion, year);

                            return getEmployeeResultsResponseDto(responseEmployee, result);
                        })
                        .filter(employeeResultsResponseDto -> employeeResultsResponseDto.getResultResponseDto().size() != 0)
                        .collect(Collectors.toList());

        return employees;
    }

    /**
     * Method that finds the results of an employee by its id, maps the results to {@link ResultResponseDto} and sets
     * the response to the specified employee
     *
     * @param responseEmployee {@link EmployeeResponseDto}
     * @param result           list of all {@link Result} that the employee has achieved
     * @return {@link EmployeeResultsResponseDto} with set {@link ResultResponseDto}
     */
    private EmployeeResultsResponseDto getEmployeeResultsResponseDto(EmployeeResultsResponseDto responseEmployee,
                                                                     List<Result> result) {
        List<ResultResponseDto> resultByEmployeeId = List.of();

        if (result != null) {
            resultByEmployeeId = result.stream().map(r -> modelMapper.map(r, ResultResponseDto.class))
                    .collect(Collectors.toList());
        }

        responseEmployee.setResultResponseDto(resultByEmployeeId);

        return responseEmployee;
    }

    /**
     * Method that converts {@link Employee} to {@link EmployeeResultsResponseDto} and sets employee's names and
     * identification number to the Dto
     *
     * @param e {@link Employee} to be converted
     * @return {@link EmployeeResultsResponseDto}
     */
    private EmployeeResultsResponseDto getEmployeeWithNamesResponseDto(Employee e) {
        EmployeeResultsResponseDto responseEmployee = modelMapper.map(e, EmployeeResultsResponseDto.class);

        String employeeNames = String.format("%s %s", e.getFirstName(), e.getLastName());
        Integer employeeIdentityNumber = e.getIdentificationNumber();

        responseEmployee.setEmployeeNames(employeeNames);
        responseEmployee.setIdentificationNumber(employeeIdentityNumber);

        return responseEmployee;
    }

}
