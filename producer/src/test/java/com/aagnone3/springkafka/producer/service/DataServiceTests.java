package com.aagnone3.springkafka.producer.service;

import static org.mockito.Mockito.when;

import com.aagnone3.springkafka.producer.domain.SourceData;
import com.aagnone3.springkafka.producer.producers.KafkaDataProducer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DataServiceTests {

    @Autowired
    private DataService dataService;

    @MockBean
    KafkaDataProducer kafkaDataProducer;

    @Test
    void testSend() throws Exception {
        // arrange
        SourceData testData = new SourceData(Long.valueOf(1), "description", true);
        when(kafkaDataProducer.send(testData)).thenReturn(null);

        // act
        SourceData ackData = dataService.send(testData);

        // assert
        assert(ackData.getId() == testData.getId());
    }
}
