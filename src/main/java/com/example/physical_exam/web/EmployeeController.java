package com.example.physical_exam.web;

import com.example.physical_exam.model.dto.request.EmployeeCreationRequestDto;
import com.example.physical_exam.model.dto.response.EmployeeResponseDto;
import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Employee Controller with endpoints related to {@link Employee} Objects
 */
@RestController
@RequestMapping("/api/v1/physical_exam/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public EmployeeResponseDto getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDto employee = employeeService.findEmployeeById(id);

        return employee;
    }

    @GetMapping("/{identityNumber}")
    public List<EmployeeResponseDto> getEmployeeByIdentificationNumber(
            @PathVariable(required = false) Integer identityNumber) {
        List<EmployeeResponseDto> employees;

        if (identityNumber == null) {
            employees = employeeService.findAllEmployees();
        } else {
            EmployeeResponseDto employeeResponseDto = employeeService.findEmployeeByIdentityNumber(identityNumber);
            employees = List.of(employeeResponseDto);
        }

        return employees;
    }

    @GetMapping
    public List<EmployeeResponseDto> getEmployeesByGender(@RequestParam(value = "gender") Gender gender) {
        List<EmployeeResponseDto> employees = employeeService.findAllByGender(gender);

        return employees;
    }

    @PostMapping
    public EmployeeResponseDto saveEmployee(@RequestBody EmployeeCreationRequestDto employeeRequestDto) {
        EmployeeResponseDto savedEmployee = employeeService.saveEmployee(employeeRequestDto);

        return savedEmployee;
    }
}
