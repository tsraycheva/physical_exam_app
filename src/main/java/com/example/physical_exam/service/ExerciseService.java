package com.example.physical_exam.service;

import com.example.physical_exam.model.dto.response.ExerciseResponseDto;
import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.enumeration.Gender;

import java.util.List;

/**
 * Class that holds all the business logic related to {@link Exercise} Objects
 */
public interface ExerciseService {

    /**
     * Method that searches for all existing exercises in the database
     *
     * @return list of all Exercise Objects wrapped into {@link ExerciseResponseDto}
     */
    List<ExerciseResponseDto> findAllExercises();

    /**
     * Method that searches for {@link Exercise} by its id
     *
     * @param id of the searched exercise
     * @return found Exercise Entity wrapped into {@link ExerciseResponseDto}
     */
    ExerciseResponseDto findExerciseById(Long id);

    /**
     * Method that searches for all existing exercises in the database by the
     * gender they are intended for
     *
     * @param gender that exercises are intended for
     * @return list of Exercise Entities wrapped into {@link ExerciseResponseDto}
     */
    List<ExerciseResponseDto> findAllByGender(Gender gender);

    /**
     * Method that searches for a specified Exercise by its name and the gender
     * it is intended for
     *
     * @param gender that Exercise is intended for
     * @param name   of the Exercise
     * @return Object of type {@link Exercise}
     */
    Exercise findExerciseByGenderAndName(Gender gender, String name);
}
