package com.example.physical_exam.model.dto.response;

import com.example.physical_exam.model.enumeration.Gender;
import com.example.physical_exam.model.entity.Exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response Dto class that is used for the output of obtaining {@link Exercise}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseResponseDto {

    private String name;
    private Integer requirement;
    private Gender gender;
}
