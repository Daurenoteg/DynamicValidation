package com.example.guarantebasic.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "guarantee")
public class Guarantee {

    @Id
    @GeneratedValue
    private UUID id;
    private Date sysCreateTime;
    private BigDecimal totalAmount;
    private BigDecimal guaranteeCost;
    private String type;

}
