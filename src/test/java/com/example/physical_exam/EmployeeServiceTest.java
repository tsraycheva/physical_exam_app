package com.example.physical_exam;

import com.example.physical_exam.creator.EmployeeCreator;
import com.example.physical_exam.creator.ResultCreator;
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
    private ResultRepository resultRepository;

    @Mock
    private ModelMapper modelMapper;

    private EmployeeCreator employeeCreator = new EmployeeCreator();

    private ResultCreator resultCreator = new ResultCreator();

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
    void whenFindAllByGender_thenFoundAll() {
        Employee maleTrifonEmployee = employeeCreator.createMaleTrifonEmployee();
        Employee malePeshoEmployee = employeeCreator.createMalePeshoEmployee();
        EmployeeResponseDto maleTrifonEmployeeDto = employeeCreator.createMaleTrifonEmployeeResponseDto();
        EmployeeResponseDto malePeshoEmployeeDto = employeeCreator.createMalePeshoEmployeeResponseDto();

        Gender gender = maleTrifonEmployee.getGender();

        List<Employee> allByGender = List.of(malePeshoEmployee, maleTrifonEmployee);

        when(employeeRepository.findAllByGenderOrderByFirstName(gender)).thenReturn(allByGender);
        when(modelMapper.map(malePeshoEmployee, EmployeeResponseDto.class)).thenReturn(malePeshoEmployeeDto);
        when(modelMapper.map(maleTrifonEmployee, EmployeeResponseDto.class)).thenReturn(maleTrifonEmployeeDto);

        List<EmployeeResponseDto> foundEmployeesByGender = employeeService.findAllByGender(gender);

        assertEquals(allByGender.size(), foundEmployeesByGender.size());
        assertEquals(gender, allByGender.get(0).getGender());
    }

    @Test
    void whenSaveEmployee_thenSaved() {
        EmployeeCreationRequestDto employeeRequestPesho = employeeCreator.createEmployeePeshoCreationRequestDto();
        Employee employeePesho = employeeCreator.createMalePeshoEmployee();
        EmployeeResponseDto employeePeshoResponse = employeeCreator.createMalePeshoEmployeeResponseDto();

        when(modelMapper.map(employeeRequestPesho, Employee.class)).thenReturn(employeePesho);
        when(employeeRepository.save(employeePesho)).thenReturn(employeePesho);
        when(modelMapper.map(employeePesho, EmployeeResponseDto.class)).thenReturn(employeePeshoResponse);

        EmployeeResponseDto savedEmployee = employeeService.saveEmployee(employeeRequestPesho);

        assertEquals(employeePeshoResponse.getFirstName(), savedEmployee.getFirstName());
        assertEquals(employeePeshoResponse.getLastName(), savedEmployee.getLastName());
        assertEquals(employeePeshoResponse.getImageUrl(), savedEmployee.getImageUrl());
        assertEquals(employeePeshoResponse.getGender(), savedEmployee.getGender());
        assertEquals(employeePeshoResponse.getPosition(), savedEmployee.getPosition());
        assertEquals(employeePeshoResponse.getIdentificationNumber(), savedEmployee.getIdentificationNumber());
    }

    @Test
    void whenFindEmployeesWithResultsConclusionNotNullYearNull_thenOk() {
        Employee employeePesho = employeeCreator.createMalePeshoEmployee();
        List<Employee> employees = List.of(employeePesho);

        EmployeeResultsResponseDto employeeResultsResponseDto = employeeCreator.createEmployeePeshoWithResults();
        List<EmployeeResultsResponseDto> employeesResults = List.of(employeeResultsResponseDto);

        Result result = resultCreator.createResult();
        List<Result> employeeResult = List.of(result);

        ResultResponseDto resultResponseDto = resultCreator.createResultResponseDto();

        when(employeeRepository.findAll()).thenReturn(employees);
        when(modelMapper.map(employeePesho, EmployeeResultsResponseDto.class)).thenReturn(employeeResultsResponseDto);
        when(resultRepository.findResultByEmployeesIdAndConclusion(null, Conclusion.PASSED)).thenReturn(employeeResult);
        when(modelMapper.map(result, ResultResponseDto.class)).thenReturn(resultResponseDto);

        List<EmployeeResultsResponseDto> foundByConclusion = employeeService.findAllEmployeesResults(Conclusion.PASSED, null);

        assertEquals(employeesResults.size(), foundByConclusion.size());
        assertEquals(employeesResults.get(0).getEmployeeNames(), foundByConclusion.get(0).getEmployeeNames());
        assertEquals(employeesResults.get(0).getIdentificationNumber(), foundByConclusion.get(0).getIdentificationNumber());
        assertEquals(employeesResults.get(0).getResultResponseDto().size(), foundByConclusion.get(0).getResultResponseDto().size());
    }

    @Test
    void whenFindAllEmployees_thenReturnAll() {
        Employee employeeMale = employeeCreator.createMalePeshoEmployee();
        Employee employeeFemale = employeeCreator.createFemaleEmployee();
        List<Employee> expectedEmployees = List.of(employeeMale, employeeFemale);

        EmployeeResponseDto employeeResponseDtoPesho = employeeCreator.createMalePeshoEmployeeResponseDto();
        EmployeeResponseDto employeeResponseDtoFemale = employeeCreator.createFemaleEmployeeResponseDto();

        when(employeeRepository.findAll()).thenReturn(expectedEmployees);
        when(modelMapper.map(employeeMale, EmployeeResponseDto.class)).thenReturn(employeeResponseDtoPesho);
        when(modelMapper.map(employeeFemale, EmployeeResponseDto.class)).thenReturn(employeeResponseDtoFemale);

        List<EmployeeResponseDto> allActualFoundEmployees = employeeService.findAllEmployees();

        Employee expectedEmployeeMale = expectedEmployees.get(0);

        assertEquals(expectedEmployees.size(), allActualFoundEmployees.size(), "Employees count must be " + expectedEmployees.size() +
                ", but actually it is " + allActualFoundEmployees.size());
        EmployeeResponseDto actualEmployeeMale = allActualFoundEmployees.get(0);
        assertEquals(expectedEmployeeMale.getGender(), actualEmployeeMale.getGender(), "Employee gender must be " +
                expectedEmployeeMale.getGender() + ", but actually it is " + actualEmployeeMale.getGender());
        assertEquals(expectedEmployeeMale.getPosition(), actualEmployeeMale.getPosition(), "Employee position must be " + expectedEmployeeMale.getPosition() +
                ", but actually it is " + actualEmployeeMale.getPosition());
        assertEquals(expectedEmployeeMale.getIdentificationNumber(), actualEmployeeMale.getIdentificationNumber(), "Employee identification number must be " +
                expectedEmployeeMale.getIdentificationNumber() + ", but actually it is " + actualEmployeeMale.getIdentificationNumber());
        assertEquals(expectedEmployeeMale.getFirstName(), actualEmployeeMale.getFirstName(), "Employee first name must be " + expectedEmployeeMale.getFirstName() +
                ", but actually it is " + actualEmployeeMale.getFirstName());
        assertEquals(expectedEmployeeMale.getLastName(), actualEmployeeMale.getLastName(), "Employee last name must be " + expectedEmployeeMale.getLastName() +
                ", but actually it is " + actualEmployeeMale.getLastName());

    }
}
