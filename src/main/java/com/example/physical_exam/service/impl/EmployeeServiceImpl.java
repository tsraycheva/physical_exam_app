package com.example.physical_exam.service.impl;

import com.example.physical_exam.exception.ResourceNotFoundException;
import com.example.physical_exam.model.dto.request.EmployeeCreationRequestDto;
import com.example.physical_exam.model.dto.response.EmployeeResponseDto;
import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.repository.EmployeeRepository;
import com.example.physical_exam.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
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
    public EmployeeResponseDto findEmployeeByIdentityNumber(Integer identificationNumber) {

        Employee employee = employeeRepository
                .findEmployeeByIdentificationNumber(identificationNumber)
                .orElse(null);

        if (employee == null) {
            log.error("When searching for an employee with identificationNumber {}, not found!", identificationNumber);

            throw new ResourceNotFoundException("Not found employee with identificationNumber " + identificationNumber);
        }

        log.info("When searching for employee with identificationNumber {}, found: {}", identificationNumber, employee.toString());

        EmployeeResponseDto employeeResponse = modelMapper.map(employee, EmployeeResponseDto.class);

        return employeeResponse;
    }

    @Override
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
    public List<EmployeeResponseDto> findAllByGender(Gender gender) {
        List<Employee> employees = employeeRepository.findAllByGenderOrderByFirstName(gender);
        List<EmployeeResponseDto> allEmployeesByGender =
                employees
                        .stream()
                        .map(e -> modelMapper.map(e, EmployeeResponseDto.class))
                        .collect(Collectors.toList());

        log.info("When searching for all employees of {} gender, there are {} found.", gender.toString(), allEmployeesByGender.size());

        return allEmployeesByGender;
    }

    @Override
    public EmployeeResponseDto saveEmployee(EmployeeCreationRequestDto employeeRequestDto) {
        Employee employeeToSave = modelMapper.map(employeeRequestDto, Employee.class);
        Employee employeeEntity = employeeRepository.save(employeeToSave);
        EmployeeResponseDto savedEmployee = modelMapper.map(employeeEntity, EmployeeResponseDto.class);

        log.info("Employee with id {} successfully saved in DB!", employeeEntity.getId());

        return savedEmployee;
    }
}
