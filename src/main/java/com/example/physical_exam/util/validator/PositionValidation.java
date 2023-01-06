package com.example.physical_exam.util.validator;

import com.example.physical_exam.model.enumeration.Position;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class PositionValidation implements ConstraintValidator<ValidPosition, Position> {

    private Position[] subset;

    @Override
    public void initialize(ValidPosition constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(Position position, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.asList(subset).contains(position);
    }
}
