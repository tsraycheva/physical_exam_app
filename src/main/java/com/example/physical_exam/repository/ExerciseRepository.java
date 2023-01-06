package com.example.physical_exam.repository;

import com.example.physical_exam.model.entity.Exercise;
import com.example.physical_exam.model.enumeration.ExerciseEnum;
import com.example.physical_exam.model.enumeration.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findAllByGender(Gender gender);

    Optional<Exercise> findExerciseByGenderAndName(Gender gender, ExerciseEnum name);
}
