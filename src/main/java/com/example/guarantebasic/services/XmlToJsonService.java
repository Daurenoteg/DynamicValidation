package com.example.guarantebasic.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class XmlToJsonService {

    private final XmlMapper xmlMapper = new XmlMapper();
    private final ObjectMapper jsonMapper = new ObjectMapper();

    public String convertXmlToJson(byte[] xmlBytes, boolean pretty) throws IOException {
        JsonNode node = xmlMapper.readTree(xmlBytes);
        return pretty
                ? jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node)
                : jsonMapper.writeValueAsString(node);
    }
}
