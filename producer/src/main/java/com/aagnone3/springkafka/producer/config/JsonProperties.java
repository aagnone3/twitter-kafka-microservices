package com.aagnone3.springkafka.producer.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties
@PropertySource(
    value = "classpath:moreProperties.json",
    factory = JsonPropertySourceFactory.class,
    ignoreResourceNotFound = true
)
public class JsonProperties {
    private int port;
    private boolean resend;
    private String host;
    private List<String> topics;
}
