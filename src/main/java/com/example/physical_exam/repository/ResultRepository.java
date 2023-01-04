package com.example.physical_exam.repository;

import com.example.physical_exam.model.entity.Result;
import com.example.physical_exam.model.enumeration.Conclusion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findAllByConclusionOrderByYearOfPerformance(Conclusion conclusion);

    List<Result> findAllByYearOfPerformanceOrderById(Integer year);

    List<Result> findAllByYearOfPerformanceAndConclusionOrderById(Integer year, Conclusion conclusion);

    List<Result> findResultByEmployeesId(Long id);

    List<Result> findResultByEmployeesIdAndConclusion(Long id, Conclusion conclusion);

    List<Result> findResultByEmployeesIdAndYearOfPerformance(Long id, Integer year);

    List<Result> findResultByEmployeesIdAndConclusionAndYearOfPerformance(Long id, Conclusion conclusion, Integer year);

    @Query("SELECT r FROM Result r " +
            "ORDER By r.yearOfPerformance")
    List<Result> findAllOrderByYearOfPerformance();
}
