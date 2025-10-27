package com.example.guarantebasic.controller;

import com.example.guarantebasic.model.Guarantee;
import com.example.guarantebasic.model.guaranteeDto.GuaranteeDto;
import com.example.guarantebasic.services.GuaranteeService;
import com.example.guarantebasic.validator.GuaranteeValidator;
import com.example.guarantebasic.validator.exception.BusinessException;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Апи для простомтра гарантий",description = "Ченибудь нажмите ченибудь пишите",
        externalDocs = @ExternalDocumentation(
                description = "Confluence страница по гарантиям",
                url = "https://confluence.company.kz/display/API/Guarantee+API"
        )
)
public class GuaranteeRestController {

    private final GuaranteeService guaranteeService;

    private final GuaranteeValidator guaranteeValidator;

    public GuaranteeRestController(GuaranteeService guaranteeService, GuaranteeValidator guaranteeValidator) {
        this.guaranteeService = guaranteeService;
        this.guaranteeValidator = guaranteeValidator;
    }

    @GetMapping
    @Operation(
            summary = "Получить список клубов",
            description = "Возвращает список всех гарантий в системе"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список гарантий успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GuaranteeDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка сервера"
            )
    })
    public List<GuaranteeDto> guaranteeDtoList(){
        return guaranteeService.findAllGuarantees();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить гарантию",
            description = "Возвращает гарантию по id в системе"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Гарантия успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GuaranteeDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка сервера"
            )
    })
    public GuaranteeDto guaranteeDto(@PathVariable long id){
        if (id <= 0) {
            throw new BusinessException("605","Идентификатор должен быть больше 0");
        }
        return guaranteeService.findByGuaranteeId(id);
    }

    @PostMapping("/guarantee/save")
    public ResponseEntity<GuaranteeDto> saveGuarantee(@RequestBody GuaranteeDto guaranteeDto) {
        guaranteeValidator.validate(guaranteeDto);
        GuaranteeDto savedGuarantee = guaranteeService.createGuarantee(guaranteeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGuarantee);
    }



}