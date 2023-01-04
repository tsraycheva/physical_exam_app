package com.example.physical_exam.service;

import com.example.physical_exam.creator.ExerciseCreator;
import com.example.physical_exam.exception.ResourceNotFoundException;
import com.example.physical_exam.model.dto.response.ExerciseResponseDto;
import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.repository.ExerciseRepository;
import com.example.physical_exam.service.impl.ExerciseServiceImpl;
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
public class ExerciseServiceTest {

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ModelMapper modelMapper;

    private ExerciseCreator exerciseCreator = new ExerciseCreator();

    @Test
    void whenGetExerciseById_thenFoundCorrectExercise() {
        Long id = 1L;
        Exercise exercise = exerciseCreator.createExerciseJumpMale();
        ExerciseResponseDto responseDto = exerciseCreator.createExerciseResponseDtoJumpMale();

        when(exerciseRepository.findById(id)).thenReturn(Optional.of(exercise));
        when(modelMapper.map(exercise, ExerciseResponseDto.class)).thenReturn(responseDto);

        ExerciseResponseDto foundExercise = exerciseService.findExerciseById(id);

        assertEquals(exercise.getRequirement(), foundExercise.getRequirement());
        assertEquals(exercise.getGender(), foundExercise.getGender());
        assertEquals(exercise.getName(), foundExercise.getName());
    }

    @Test
    void whenFindByGenderAndName_thenFoundCorrectExercise() {
        Exercise exercise = exerciseCreator.createExerciseJumpMale();
        Gender gender = exercise.getGender();
        String name = exercise.getName();

        when(exerciseRepository.findExerciseByGenderAndName(gender, name)).thenReturn(Optional.of(exercise));

        Exercise foundExercise = exerciseService.findExerciseByGenderAndName(gender, name);

        assertEquals(exercise.getName(), foundExercise.getName());
        assertEquals(exercise.getGender(), foundExercise.getGender());
        assertEquals(exercise.getRequirement(), foundExercise.getRequirement());
    }

    @Test
    void whenFindAllExercises_thenFoundAll() {
        Exercise jump = exerciseCreator.createExerciseJumpMale();
        Exercise crunches = exerciseCreator.createExerciseCrunchesFemale();
        ExerciseResponseDto responseDtoJump = exerciseCreator.createExerciseResponseDtoJumpMale();
        ExerciseResponseDto responseDtoCrunches = exerciseCreator.createExerciseResponseDtoCrunchesFemale();

        List<Exercise> allExercises = List.of(jump, crunches);

        when(exerciseRepository.findAll()).thenReturn(allExercises);
        when(modelMapper.map(jump, ExerciseResponseDto.class)).thenReturn(responseDtoJump);
        when(modelMapper.map(crunches, ExerciseResponseDto.class)).thenReturn(responseDtoCrunches);

        List<ExerciseResponseDto> foundExercises = exerciseService.findAllExercises();

        assertEquals(allExercises.size(), foundExercises.size());
    }

    @Test
    void whenFindAllExercisesByGender_thenFoundCorrect() {
        Exercise crunches = exerciseCreator.createExerciseCrunchesFemale();
        ExerciseResponseDto responseDtoCrunches = exerciseCreator.createExerciseResponseDtoCrunchesFemale();
        Gender gender = crunches.getGender();

        List<Exercise> allFemale = List.of(crunches);

        when(exerciseRepository.findAllByGender(gender)).thenReturn(allFemale);
        when(modelMapper.map(crunches, ExerciseResponseDto.class)).thenReturn(responseDtoCrunches);

        List<ExerciseResponseDto> foundAllByGender = exerciseService.findAllByGender(gender);

        assertEquals(allFemale.size(), foundAllByGender.size());
    }

    @Test
    void whenFindByGenderAndName_thenNotFoundException() {
        Gender gender = Gender.FEMALE;
        String name = "jump";

        when(exerciseRepository.findExerciseByGenderAndName(gender, name))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> exerciseService.findExerciseByGenderAndName(gender, name));
    }

    @Test
    void whenFindById_thenNotFoundException() {
        Long id = 1L;

        when(exerciseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> exerciseService.findExerciseById(id));
    }

}
