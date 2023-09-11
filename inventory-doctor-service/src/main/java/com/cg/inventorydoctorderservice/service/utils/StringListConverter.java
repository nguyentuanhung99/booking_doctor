package com.cg.inventorydoctorderservice.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        try {
            return new ObjectMapper().writeValueAsString(stringList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
    }

    @SneakyThrows
    @Override
    public List<String> convertToEntityAttribute(String string) {
        if (string == null) {
            return null;
        } else {
            return new ObjectMapper().readValue(string, new TypeReference<List<String>>() {
            });
        }
    }
}