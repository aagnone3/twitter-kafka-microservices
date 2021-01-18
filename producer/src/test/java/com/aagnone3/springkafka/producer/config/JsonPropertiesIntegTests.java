package com.aagnone3.springkafka.producer.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JsonPropertiesIntegTests {

    @Autowired
    private JsonProperties jsonProperties;
    
	@Test
	void loadsJsonProperties() throws Exception {
        assertEquals("localhost", jsonProperties.getHost());
        assertEquals(8081, jsonProperties.getPort());
        assertEquals(true, jsonProperties.isResend());
        assertEquals(List.of("topicA", "topicB"), jsonProperties.getTopics());
	}

}
