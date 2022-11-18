package com.example.physical_exam.web;

import com.example.physical_exam.exception.CanNotPerformOperationException;
import com.example.physical_exam.exception.ResourceNotFoundException;
import com.example.physical_exam.model.dto.response.ExerciseResponseDto;
import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.service.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Exercise Controller with endpoints related to {@link Exercise} Objects
 */
@RestController
@RequestMapping("/api/v1/physical_exam/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    /**
     * Endpoint for making a request to search for a specified exercise by its id
     *
     * @param id of exercise to find
     * @return {@link ExerciseResponseDto} representing successfully found exercise
     */
    @Operation(
            tags  = "exercise",
            summary = "Method for searching an exercise by id.",
            operationId = "findExerciseById",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "id of the searched exercise", example = "1")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ExerciseResponseDto.class),
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
    public ResponseEntity<ExerciseResponseDto> getExerciseById(@PathVariable Long id) {
        ExerciseResponseDto exerciseResponse = exerciseService.findExerciseById(id);

        return new ResponseEntity<>(exerciseResponse, HttpStatus.OK);
    }

    /**
     * Endpoint for making a request to search for all exercises in database
     *
     * @return list of all {@link ExerciseResponseDto} found in database
     */
    @Operation(
            tags  = "exercise",
            summary = "Method for searching all exercises in database.",
            operationId = "findAllExercises",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ExerciseResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Successful operation!"),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = CanNotPerformOperationException.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Operation failed!")})
    @GetMapping("/all")
    public ResponseEntity<List<ExerciseResponseDto>> getAllExercises() {
        List<ExerciseResponseDto> exercises;
        exercises = exerciseService.findAllExercises();

        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    /**
     * Endpoint for making a request to search for all exercises in database meant for a specified gender
     *
     * @param gender that exercises are meant for
     * @return list of all {@link ExerciseResponseDto} found in database that are obligatory for the specified gender
     */
    @Operation(
            tags  = "exercise",
            summary = "Method for searching all exercises in database that are meant for a specified gender.",
            operationId = "findAllExercisesByGender",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ExerciseResponseDto.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Successful operation!"),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = CanNotPerformOperationException.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Operation failed!")})
    @GetMapping
    public ResponseEntity<List<ExerciseResponseDto>> getAllExercisesByGender(
            @RequestParam (value = "gender") Gender gender)  {
        List<ExerciseResponseDto> exercisesByGender = exerciseService.findAllByGender(gender);

        return new ResponseEntity<>(exercisesByGender, HttpStatus.OK);
    }

}
