package com.example.physical_exam;

import com.example.physical_exam.creator.EmployeeCreator;
import com.example.physical_exam.exception.ResourceNotFoundException;
import com.example.physical_exam.model.dto.response.EmployeeResponseDto;
import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.repository.EmployeeRepository;
import com.example.physical_exam.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    private EmployeeCreator employeeCreator = new EmployeeCreator();

    @Test
    void whenFindEmployeeById_thenFoundEmployee() {
        Long id = 1L;
        Employee employee = employeeCreator.createFemaleEmployee();
        EmployeeResponseDto responseDto = employeeCreator.createFemaleEmployeeResponseDto();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(modelMapper.map(employee, EmployeeResponseDto.class)).thenReturn(responseDto);

        EmployeeResponseDto foundEmployee = employeeService.findEmployeeById(id);

        assertEquals(employee.getFirstName(), foundEmployee.getFirstName());
        assertEquals(employee.getLastName(), foundEmployee.getLastName());
        assertEquals(employee.getGender(), foundEmployee.getGender());
        assertEquals(employee.getIdentificationNumber(), foundEmployee.getIdentificationNumber());
        assertEquals(employee.getImageUrl(), foundEmployee.getImageUrl());
        assertEquals(employee.getPosition(), foundEmployee.getPosition());
    }

    @Test
    void whenFindEmployeeById_thenNotFoundException() {
        Long id = 1L;

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.findEmployeeById(id));
    }

    @Test
    void whenFindAll_thenFoundAll() {
        Employee maleEmployee = employeeCreator.createMaleTrifonEmployee();
        Employee femaleEmployee = employeeCreator.createFemaleEmployee();
        EmployeeResponseDto maleEmployeeDto = employeeCreator.createMaleTrifonEmployeeResponseDto();
        EmployeeResponseDto femaleEmployeeDto = employeeCreator.createFemaleEmployeeResponseDto();

        List<Employee> allEmployees = List.of(maleEmployee, femaleEmployee);

        when(employeeRepository.findAll()).thenReturn(allEmployees);
        when(modelMapper.map(maleEmployee, EmployeeResponseDto.class)).thenReturn(maleEmployeeDto);
        when(modelMapper.map(femaleEmployee, EmployeeResponseDto.class)).thenReturn(femaleEmployeeDto);

        List<EmployeeResponseDto> allEmployeesDto = employeeService.findAllEmployees();

        assertEquals(allEmployees.size(), allEmployeesDto.size());
    }

    @Test
    void whenFindAllByGender_thenFoundAll() {
        Employee maleTrifonEmployee = employeeCreator.createMaleTrifonEmployee();
        Employee malePeshoEmployee = employeeCreator.createMalePeshoEmployee();
        EmployeeResponseDto maleTrifonEmployeeDto = employeeCreator.createMaleTrifonEmployeeResponseDto();
        EmployeeResponseDto malePeshoEmployeeDto = employeeCreator.createMalePeshoEmployeeResponseDto();

        Gender gender = maleTrifonEmployee.getGender();

        List<Employee> allByGender = List.of(maleTrifonEmployee, malePeshoEmployee);

        when(employeeRepository.findAllByGenderOrderByFirstName(gender)).thenReturn(allByGender);
        when(modelMapper.map(malePeshoEmployee, EmployeeResponseDto.class)).thenReturn(malePeshoEmployeeDto);
        when(modelMapper.map(maleTrifonEmployee, EmployeeResponseDto.class)).thenReturn(maleTrifonEmployeeDto);


        List<EmployeeResponseDto> foundEmployeesByGender = employeeService.findAllByGender(gender);

        assertEquals(allByGender.size(), foundEmployeesByGender.size());
    }
}
