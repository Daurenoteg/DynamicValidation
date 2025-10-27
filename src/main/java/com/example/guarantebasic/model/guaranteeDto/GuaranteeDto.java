package com.example.guarantebasic.model.guaranteeDto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class GuaranteeDto {
    private Long docNumber;
    private Date sysCreateTime;
    private BigDecimal totalAmount;
    private BigDecimal guaranteeCost;
    private String type;
}

