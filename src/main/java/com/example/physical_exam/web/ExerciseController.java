package com.example.physical_exam.web;

import com.example.physical_exam.model.dto.response.ExerciseResponseDto;
import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.service.ExerciseService;
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

    @GetMapping("/{id}")
    public ExerciseResponseDto getExerciseById(@PathVariable Long id) {
        ExerciseResponseDto exerciseResponse = exerciseService.findExerciseById(id);

        return exerciseResponse;
    }

    @GetMapping("/all")
    public List<ExerciseResponseDto> getAllExercises() {
        List<ExerciseResponseDto> exercises;
        exercises = exerciseService.findAllExercises();

        return exercises;
    }

    @GetMapping
    public List<ExerciseResponseDto> getAllExercisesByGender(
            @RequestParam (value = "gender") Gender gender)  {
        List<ExerciseResponseDto> exercisesByGender = exerciseService.findAllByGender(gender);

        return exercisesByGender;
    }

}
