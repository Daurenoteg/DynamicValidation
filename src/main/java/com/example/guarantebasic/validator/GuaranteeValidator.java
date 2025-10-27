package com.example.guarantebasic.validator;

import com.example.guarantebasic.model.guaranteeDto.GuaranteeDto;
import com.example.guarantebasic.validator.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class GuaranteeValidator {
    private final ValidationProperties validationProperties;

    public GuaranteeValidator(ValidationProperties validationProperties) {
        this.validationProperties = validationProperties;
    }

    public void validate(GuaranteeDto dto) {
        if (validationProperties.isEnabled("control1")) {
            if (dto == null) {
                throw new ValidationException("VALIDATION_NULL", "Объект GuaranteeDto не может быть null");
            }
        }

        if (validationProperties.isEnabled("control2")) {
            if (dto.getDocNumber() == 3) {
                throw new ValidationException("VALIDATION_EMPTY_ID", "У ТЕБЯ ПУСТАЯ ID ШАКАЛ");
            }
        }
    }
}
