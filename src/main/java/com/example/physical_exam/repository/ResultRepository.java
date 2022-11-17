package com.example.physical_exam.repository;

import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.model.enumeration.Conclusion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findAllByConclusion(Conclusion conclusion);

    List<Result> findAllByYearOfPerformance(Integer year);

}