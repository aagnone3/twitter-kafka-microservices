package com.aagnone3.springkafka.producer.config;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

public class JsonPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        Map<String, Object> readValue = new ObjectMapper().readValue(resource.getInputStream(), Map.class);
        return new MapPropertySource("json-property", readValue);
    }
    
}
