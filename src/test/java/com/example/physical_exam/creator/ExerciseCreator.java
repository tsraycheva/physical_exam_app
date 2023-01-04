package com.example.physical_exam.creator;

import com.example.physical_exam.model.dto.response.ExerciseResponseDto;
import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.enumeration.Gender;

/**
 * Class that is used to create {@link Exercise} and {@link ExerciseResponseDto} for test purposes
 */
public class ExerciseCreator {

    /**
     * Method that creates new {@link Exercise} long jump for test purposes
     *
     * @return {@link Exercise}
     */
    public Exercise createExerciseJumpMale() {
        return new Exercise("long jump", 200, Gender.MALE);
    }

    /**
     * Method that creates new {@link ExerciseResponseDto} long jump for test purposes
     *
     * @return {@link ExerciseResponseDto}
     */
    public ExerciseResponseDto createExerciseResponseDtoJumpMale() {
        return new ExerciseResponseDto("long jump", 200, Gender.MALE);
    }

    /**
     * Method that creates new {@link Exercise} long jump for test purposes for Female
     *
     * @return {@link Exercise}
     */
    public Exercise createExerciseJumpFemale() {
        return new Exercise("long jump", 180, Gender.MALE);
    }


    /**
     * Method that creates new {@link Exercise} crunches for test purposes for Female
     *
     * @return {@link Exercise}
     */
    public Exercise createExerciseCrunchesFemale() {
        return new Exercise("crunches", 30, Gender.FEMALE);
    }

    /**
     * Method that creates new {@link ExerciseResponseDto} crunches for test purposes for Female
     *
     * @return {@link ExerciseResponseDto}
     */
    public ExerciseResponseDto createExerciseResponseDtoCrunchesFemale() {
        return new ExerciseResponseDto("crunches", 30, Gender.FEMALE);
    }

    /**
     * Method that creates new {@link Exercise} running for test purposes for Female
     *
     * @return {@link Exercise}
     */
    public Exercise createExerciseRunningFemale() {
        return new Exercise("800 meters run", 270, Gender.FEMALE);
    }

    /**
     * Method that creates new {@link Exercise} push-ups for test purposes for Female
     *
     * @return {@link Exercise}
     */
    public Exercise createExercisePushUpsFemale() {
        return new Exercise("push-ups", 10, Gender.FEMALE);
    }
}
