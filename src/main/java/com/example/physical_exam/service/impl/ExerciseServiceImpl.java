package com.example.physical_exam.service.impl;

import com.example.physical_exam.model.dto.response.ExerciseResponseDto;
import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.repository.ExerciseRepository;
import com.example.physical_exam.service.ExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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

        return allExercises;
    }

    @Override
    public ExerciseResponseDto findExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id).orElse(null);
        ExerciseResponseDto exerciseResponse = modelMapper.map(exercise, ExerciseResponseDto.class);

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

        return allExercisesByGender;
    }

    @Override
    public Exercise findExerciseByGenderAndName(Gender gender, String name) {
        Exercise exercise = exerciseRepository.findExerciseByGenderAndName(gender, name).orElse(null);

        return exercise;
    }
}
