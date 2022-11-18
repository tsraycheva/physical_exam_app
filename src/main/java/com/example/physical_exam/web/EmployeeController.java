package com.example.physical_exam.web;

import com.example.physical_exam.exception.CanNotPerformOperationException;
import com.example.physical_exam.exception.ResourceNotFoundException;
import com.example.physical_exam.model.dto.request.EmployeeCreationRequestDto;
import com.example.physical_exam.model.dto.response.EmployeeResponseDto;
import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
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

    /**
     * Endpoint for making a request to search for a specified employee by its id
     *
     * @param id of the searched employee
     * @return found {@link EmployeeResponseDto}
     */
    @Operation(
            tags  = "employee",
            summary = "Method for searching an employee by id.",
            operationId = "findEmployeeById",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "id of the searched employee", example = "1")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Successful operation!"),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = CanNotPerformOperationException.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Operation failed!"),
                    @ApiResponse(responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Not Found!")})
    @GetMapping("/{id}")
    public EmployeeResponseDto getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDto employee = employeeService.findEmployeeById(id);

        return employee;
    }

    /**
     * Endpoint for making a request to search for a specified employee by its identification number
     *
     * @param identityNumber of the searched employee
     * @return found {@link EmployeeResponseDto}  if the identification number is passed. If it is not -
     * the result will be a list of all employees in database
     */
    @Operation(
            tags  = "employee",
            summary = "Method for searching an employee by identificationNumber. " +
                    "If identificationNumber is not passed, the response will hold all employees in database.",
            operationId = "findEmployeeByIdentityNumber",
            parameters = {@Parameter(in = ParameterIn.QUERY, name = "identityNumber", description = "identificationNumber of the searched employee", example = "1")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Successful operation!"),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = CanNotPerformOperationException.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Operation failed!"),
                    @ApiResponse(responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Not Found!")})
    @GetMapping("/identityNumber")
    public List<EmployeeResponseDto> getEmployeeByIdentificationNumber(@RequestParam(required = false, name = "identityNumber") Integer identityNumber) {

        List<EmployeeResponseDto> employees;

        if (identityNumber == null) {
            employees = employeeService.findAllEmployees();
        } else {
            EmployeeResponseDto employeeResponseDto = employeeService.findEmployeeByIdentityNumber(identityNumber);
            employees = List.of(employeeResponseDto);
        }

        return employees;
    }

    /**
     * Endpoint for making a request to find all employees by specified gender
     *
     * @param gender {@link Gender} of the searched employees
     * @return list of {@link EmployeeResponseDto} that holds all the found employees from the given gender.
     * If there are no employees from the given gender, the result will be an empty list
     */
    @Operation(
            tags  = "employee",
            summary = "Method for searching all employees by gender.",
            operationId = "findAllEmployeesByGender",
            parameters = {
                    @Parameter(
                            in = ParameterIn.QUERY, name = "gender",
                            description = "gender of the searched employees", example = "FEMALE")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Successful operation!"),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = CanNotPerformOperationException.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Operation failed!")})
    @GetMapping
    public List<EmployeeResponseDto> getEmployeesByGender(@RequestParam(value = "gender") Gender gender) {
        List<EmployeeResponseDto> employees = employeeService.findAllByGender(gender);

        return employees;
    }

    /**
     * Endpoint for making a request to save a new employee in database
     *
     * @param employeeRequestDto {@link EmployeeCreationRequestDto} with all needed data for saving an employee in database
     * @return saved employee wrapped in {@link EmployeeResponseDto}
     */
    @Operation(
            tags  = "employee",
            summary = "Method for saving an employee",
            operationId = "saveEmployee",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data transfer object to save an employee.",
                    content = @Content(schema = @Schema(implementation = EmployeeCreationRequestDto.class))),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Successful operation!"),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = CanNotPerformOperationException.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Operation failed!")})
    @PostMapping
    public EmployeeResponseDto saveEmployee(@RequestBody EmployeeCreationRequestDto employeeRequestDto) {
        EmployeeResponseDto savedEmployee = employeeService.saveEmployee(employeeRequestDto);

        return savedEmployee;
    }
}
