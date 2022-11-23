package com.example.physical_exam.creator;

import com.example.physical_exam.model.dto.request.EmployeeCreationRequestDto;
import com.example.physical_exam.model.dto.response.EmployeeResponseDto;
import com.example.physical_exam.model.dto.response.EmployeeResultsResponseDto;
import com.example.physical_exam.model.dto.response.ResultResponseDto;
import com.example.physical_exam.model.entity.Employee;
import com.example.physical_exam.model.enumeration.Conclusion;
import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.model.enumeration.Position;

import java.util.List;

/**
 * Class that is used to create {@link Employee}, {@link EmployeeResponseDto}, {@link EmployeeCreationRequestDto},
 *  {@link EmployeeResultsResponseDto} for test purposes
 */
public class EmployeeCreator {

    /**
     * Method that creates {@link Employee} from MALE {@link Gender} for test purposes
     *
     * @return {@link Employee}
     */
    public Employee createMaleTrifonEmployee() {
        return new Employee(
                "Trifon",
                "Trifonov",
                98654,
                "https://trifonimage.jpeg",
                Gender.MALE,
                Position.SENIOR_FIREFIGHTER);
    }

    /**
     * Method that creates {@link Employee} from MALE {@link Gender} for test purposes
     *
     * @return {@link Employee}
     */
    public Employee createMalePeshoEmployee() {
        return new Employee(
                "Pesho",
                "Trifonov",
                98688,
                "https://peshoimage.jpeg",
                Gender.MALE,
                Position.SENIOR_FIREFIGHTER);
    }

    /**
     * Method that creates {@link Employee} from FEMALE {@link Gender} for test purposes
     *
     * @return {@link Employee}
     */
    public Employee createFemaleEmployee() {
        return new Employee(
                "Radina",
                "Trifonova",
                98666,
                "https://radinaimage.jpeg",
                Gender.FEMALE,
                Position.JUNIOR_FIREFIGHTER);
    }

    /**
     * Method that creates {@link EmployeeResponseDto} from FEMALE {@link Gender} for test purposes
     *
     * @return {@link EmployeeResponseDto}
     */
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

    /**
     * Method that creates {@link EmployeeResponseDto} from MALE {@link Gender} for test purposes
     *
     * @return {@link EmployeeResponseDto}
     */
    public EmployeeResponseDto createMaleTrifonEmployeeResponseDto() {
        return new EmployeeResponseDto(
                "Trifon",
                "Trifonov",
                98654,
                "https://trifonimage.jpeg",
                Gender.MALE,
                Position.SENIOR_FIREFIGHTER);
    }

    /**
     * Method that creates {@link EmployeeResponseDto} from MALE {@link Gender} for test purposes
     *
     * @return {@link EmployeeResponseDto}
     */
    public EmployeeResponseDto createMalePeshoEmployeeResponseDto() {
        return new EmployeeResponseDto(
                "Pesho",
                "Trifonov",
                98688,
                "https://peshoimage.jpeg",
                Gender.MALE,
                Position.SENIOR_FIREFIGHTER);
    }

    /**
     * Method that creates {@link EmployeeCreationRequestDto} from MALE {@link Gender} for test purposes
     *
     * @return {@link EmployeeCreationRequestDto}
     */
    public EmployeeCreationRequestDto createEmployeePeshoCreationRequestDto() {
        return new EmployeeCreationRequestDto(
                "Pesho",
                "Trifonov",
                98688,
                "https://peshoimage.jpeg",
                Gender.MALE,
                Position.SENIOR_FIREFIGHTER
        );
    }

    /**
     * Method that creates {@link EmployeeResultsResponseDto} from MALE {@link Gender}
     * and {@link Conclusion} PASSED for test purposes
     *
     * @return {@link EmployeeResultsResponseDto}
     */
    public EmployeeResultsResponseDto createEmployeePeshoWithResults() {
        ResultResponseDto result = new ResultResponseDto(2020,
                230,
                44,
                45,
                210,
                Conclusion.PASSED);

        return new EmployeeResultsResponseDto(
                "Pesho Trifonov",
                98688,
                List.of(result));
    }
}
