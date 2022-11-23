package com.example.physical_exam.creator;

import com.example.physical_exam.model.dto.response.EmployeeResponseDto;
import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.model.enumeration.Position;

/**
 * Class that is used to create
 */
public class EmployeeCreator {

    public Employee createMaleTrifonEmployee() {
        return new Employee(
                "Trifon",
                "Trifonov",
                98654,
                "https://trifonimage.jpeg",
                Gender.MALE,
                Position.SENIOR_FIREFIGHTER);
    }

    public Employee createMalePeshoEmployee() {
        return new Employee(
                "Pesho",
                "Trifonov",
                98688,
                "https://peshoimage.jpeg",
                Gender.MALE,
                Position.SENIOR_FIREFIGHTER);
    }

    public Employee createFemaleEmployee() {
        return new Employee(
                "Radina",
                "Trifonova",
                98666,
                "https://radinaimage.jpeg",
                Gender.FEMALE,
                Position.JUNIOR_FIREFIGHTER);
    }

    public EmployeeResponseDto createFemaleEmployeeResponseDto() {
        return new EmployeeResponseDto(
                "Radina",
                "Trifonova",
                98666,
                "https://radinaimage.jpeg",
                Gender.FEMALE,
                Position.JUNIOR_FIREFIGHTER
        );
    }

    public EmployeeResponseDto createMaleTrifonEmployeeResponseDto() {
        return new EmployeeResponseDto(
                "Trifon",
                "Trifonov",
                98654,
                "https://trifonimage.jpeg",
                Gender.MALE,
                Position.SENIOR_FIREFIGHTER);
    }

    public EmployeeResponseDto createMalePeshoEmployeeResponseDto() {
        return new EmployeeResponseDto(
                "Pesho",
                "Trifonov",
                98688,
                "https://peshoimage.jpeg",
                Gender.MALE,
                Position.SENIOR_FIREFIGHTER);
    }
}
