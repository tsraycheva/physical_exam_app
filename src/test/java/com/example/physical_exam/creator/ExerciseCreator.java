package com.example.physical_exam.creator;

import com.example.physical_exam.model.dto.response.ExerciseResponseDto;
import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.enumeration.Gender;

/**
 * Class that is used to create {@link Exercise} and {@link ExerciseResponseDto} for test purposes
 */
public class ExerciseCreator {

    /**
     * Method that creates new {@link Exercise} for test purposes
     *
     * @return {@link Exercise}
     */
    public Exercise createExerciseJump() {
        return new Exercise("long jump", 200, Gender.MALE);
    }

    /**
     * Method that creates new {@link ExerciseResponseDto} for test purposes
     *
     * @return {@link ExerciseResponseDto}
     */
    public ExerciseResponseDto createExerciseResponseDtoJump() {
        return new ExerciseResponseDto("long jump", 200, Gender.MALE);
    }

    /**
     * Method that creates new {@link Exercise} for test purposes
     *
     * @return {@link Exercise}
     */
    public Exercise createExerciseCrunches() {
        return new Exercise("crunches", 40, Gender.FEMALE);
    }

    /**
     * Method that creates new {@link ExerciseResponseDto} for test purposes
     *
     * @return {@link ExerciseResponseDto}
     */
    public ExerciseResponseDto createExerciseResponseDtoCrunches() {
        return new ExerciseResponseDto("crunches", 40, Gender.FEMALE);
    }
}
