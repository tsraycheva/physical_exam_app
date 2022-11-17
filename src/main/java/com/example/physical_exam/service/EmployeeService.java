package com.example.physical_exam.service;

import com.example.physical_exam.model.dto.request.EmployeeCreationRequestDto;
import com.example.physical_exam.model.dto.response.EmployeeResponseDto;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.model.entity.Employee;

import java.util.List;

/**
 * Class that holds all the business logic related to Employee Objects
 */
public interface EmployeeService {

    /**
     * Method that searches for employee by his/her id
     *
     * @param id of the {@link Employee}
     * @return found Employee wrapped into {@link EmployeeResponseDto}
     */
    EmployeeResponseDto findEmployeeById(Long id);

    /**
     * Method that searches for employee by his/her identificationNumber
     *
     * @param identificationNumber of the {@link Employee}
     * @return found Employee wrapped into {@link EmployeeResponseDto}
     */
    EmployeeResponseDto findEmployeeByIdentityNumber(Integer identificationNumber);

    /**
     * Method that searches for all employees in the database
     *
     * @return list of Employee Objects wrapped into {@link EmployeeResponseDto}
     */
    List<EmployeeResponseDto> findAllEmployees();

    /**
     * Method that searches for employee by gender
     *
     * @param gender of the {@link Employee}
     * @return list of Employee Objects with specified gender wrapped into {@link EmployeeResponseDto}
     */
    List<EmployeeResponseDto> findAllByGender(Gender gender);

    /**
     * Method that serves for saving an {@link Employee}
     *
     * @param employeeRequestDto with needed data for saving an {@link Employee} Entity
     * @return saved Employee wrapped into {@link EmployeeResponseDto}
     */
    EmployeeResponseDto saveEmployee(EmployeeCreationRequestDto employeeRequestDto);
}
