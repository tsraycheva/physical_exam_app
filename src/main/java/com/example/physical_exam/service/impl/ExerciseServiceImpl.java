package com.example.physical_exam.service.impl;

import com.example.physical_exam.exception.ResourceNotFoundException;
import com.example.physical_exam.model.dto.response.ExerciseResponseDto;
import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.repository.ExerciseRepository;
import com.example.physical_exam.service.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ModelMapper modelMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ModelMapper modelMapper) {
        this.exerciseRepository = exerciseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ExerciseResponseDto> findAllExercises() {
        List<ExerciseResponseDto> allExercises =
                exerciseRepository
                        .findAll()
                        .stream()
                        .map(e -> modelMapper.map(e, ExerciseResponseDto.class))
                        .collect(Collectors.toList());

        log.info("When searching for all exercises in database, found {}.", allExercises.size());

        return allExercises;
    }

    @Override
    public ExerciseResponseDto findExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id).orElse(null);

        if (exercise == null) {
            log.error("When searching for exercise by id {}, there isn't found.", id);

            throw new ResourceNotFoundException("Not found exercise with id " + id);
        }

        ExerciseResponseDto exerciseResponse = modelMapper.map(exercise, ExerciseResponseDto.class);

        log.info("When searching for exercise with id {}, found {}.", id, exercise.toString());

        return exerciseResponse;
    }

    @Override
    public List<ExerciseResponseDto> findAllByGender(Gender gender) {
        List<Exercise> exercises = exerciseRepository.findAllByGender(gender);
        List<ExerciseResponseDto> allExercisesByGender =
                exercises
                        .stream()
                        .map(e -> modelMapper.map(e, ExerciseResponseDto.class))
                        .collect(Collectors.toList());

        log.info("When searching for all exercises meant for gender {}, found {}.", gender, exercises.size());

        return allExercisesByGender;
    }

    @Override
    public Exercise findExerciseByGenderAndName(Gender gender, String name) {
        Exercise exercise = exerciseRepository.findExerciseByGenderAndName(gender, name).orElse(null);

        if (exercise == null) {
            log.error("When searching for an exercise with name {} for gender {}, there is no exercise found.", name, gender);

            throw new ResourceNotFoundException(String.format("Not found exercise with name %s meant for gender %s.", name, gender));
        }

        log.info("When searching for an exercise with name {} for gender {}, there is an exercise found {}.", name, gender, exercise.toString());

        return exercise;
    }
}
