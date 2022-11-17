package com.example.physical_exam.repository;

import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.enumeration.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findEmployeeByIdentificationNumber(Integer identityNumber);

    List<Employee> findAllByGenderOrderByFirstName(Gender gender);
}
