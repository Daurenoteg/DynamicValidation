package com.example.guarantebasic.services;

import com.example.guarantebasic.model.Guarantee;
import com.example.guarantebasic.model.guaranteeDto.GuaranteeDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface GuaranteeService {

    List<GuaranteeDto> findAllGuarantees();

    GuaranteeDto findByGuaranteeId(long guaranteeId);

    GuaranteeDto createGuarantee(GuaranteeDto guaranteeDto);
}
