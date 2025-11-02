package com.example.guarantebasic.controller;

import com.example.guarantebasic.model.guaranteeDto.GuaranteeDto;
import com.example.guarantebasic.services.GuaranteeService;
import com.example.guarantebasic.services.XmlToJsonService;
import com.example.guarantebasic.validator.GuaranteeValidator;
import com.example.guarantebasic.validator.exception.BusinessException;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Апи для простомтра гарантий",description = "Ченибудь нажмите ченибудь пишите",
        externalDocs = @ExternalDocumentation(
                description = "Confluence страница по гарантиям",
                url = "https://confluence.company.kz/display/API/Guarantee+API"
        )
)
public class GuaranteeRestController {

    private final GuaranteeService guaranteeService;
    private final XmlToJsonService xmlToJsonService ;

    private final GuaranteeValidator guaranteeValidator;

    public GuaranteeRestController(GuaranteeService guaranteeService, XmlToJsonService xmlToJsonService, GuaranteeValidator guaranteeValidator) {
        this.guaranteeService = guaranteeService;
        this.xmlToJsonService = xmlToJsonService;
        this.guaranteeValidator = guaranteeValidator;
    }

    // 📋 READ (все)
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

    // 🔍 READ (по ID)
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

    // ✅ CREATE
    @PostMapping("/save")
    public ResponseEntity<GuaranteeDto> saveGuarantee(@RequestBody GuaranteeDto guaranteeDto) {
        guaranteeValidator.validate(guaranteeDto);
        GuaranteeDto saved = guaranteeService.createGuarantee(guaranteeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ✏️ UPDATE
    @PostMapping("/update")
    public ResponseEntity<GuaranteeDto> updateGuarantee(@PathVariable UUID id, @RequestBody GuaranteeDto guaranteeDto){
        guaranteeValidator.validate(guaranteeDto);
        GuaranteeDto updated = guaranteeService.updateGuarantee(id, guaranteeDto);
        return ResponseEntity.ok(updated);
    }

    // ❌ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuarantee(@PathVariable Long id) {
        guaranteeService.deleteGuarantee(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/xml-to-json",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE}
    )
    public ResponseEntity<String> xmlToJson(@RequestBody byte[] xmlBytes,
                                            @RequestParam(value = "pretty", defaultValue = "false") boolean pretty) {
        try {
            String json = xmlToJsonService.convertXmlToJson(xmlBytes, pretty);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(json, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Invalid XML: " + e.getMessage());
        }
    }

    @PostMapping(value = "/file-to-json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> fileToJson(@RequestPart("file") MultipartFile file,
                                             @RequestParam(value = "pretty", defaultValue = "false") boolean pretty) {
        try {
            String json = xmlToJsonService.convertXmlToJson(file.getBytes(), pretty);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(json, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Failed to read file / invalid XML: " + e.getMessage());
        }
    }
}