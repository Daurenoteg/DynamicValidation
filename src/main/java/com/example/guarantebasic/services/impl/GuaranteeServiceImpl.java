package com.example.guarantebasic.services.impl;

import com.example.guarantebasic.model.Guarantee;
import com.example.guarantebasic.model.guaranteeDto.GuaranteeDto;
import com.example.guarantebasic.repository.GuaranteeRepository;
import com.example.guarantebasic.services.GuaranteeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuaranteeServiceImpl implements GuaranteeService {
    private GuaranteeRepository guaranteeRepository;
    public GuaranteeServiceImpl(GuaranteeRepository guaranteeRepository) {
        this.guaranteeRepository = guaranteeRepository;
    }

    @Override
    public List<GuaranteeDto> findAllGuarantees() {
        List<Guarantee> guarantees = guaranteeRepository.findAll();
        return guarantees.stream()
                .map(this::mapToGuaranteeDto)
                .collect(Collectors.toList());
    }

    @Override
    public GuaranteeDto findByGuaranteeId(long guaranteeId) {
        Guarantee guarantee = guaranteeRepository.findById(guaranteeId)
                .orElseThrow(() -> new EntityNotFoundException("Клуб с ID " + guaranteeId + " не найден"));
        return mapToGuaranteeDto(guarantee);
    }

    @Override
    public GuaranteeDto createGuarantee(GuaranteeDto guaranteeDto) {
        Guarantee guarantee = mapDtoToGuarantee(guaranteeDto);
        Guarantee savedGuarantee = guaranteeRepository.save(guarantee);
        return mapToGuaranteeDto(savedGuarantee);
    }

    private Guarantee mapDtoToGuarantee(GuaranteeDto guaranteeDto) {
        return Guarantee.builder()
                .type(guaranteeDto.getType())
                .guaranteeCost(guaranteeDto.getGuaranteeCost())
                .sysCreateTime(guaranteeDto.getSysCreateTime())
                .build();
    }

    private GuaranteeDto mapToGuaranteeDto(Guarantee guarantee) {
        return GuaranteeDto.builder()
                .type(guarantee.getType())
                .guaranteeCost(guarantee.getGuaranteeCost())
                .sysCreateTime(guarantee.getSysCreateTime())
                .build();
    }

}
