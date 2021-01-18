package com.aagnone3.springkafka.producer.producers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.aagnone3.springkafka.producer.domain.SourceData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EmbeddedKafka(topics = { KafkaDataProducerTests.TOPIC })
@TestPropertySource(properties = { "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}" })
public class KafkaDataProducerTests {

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    KafkaDataProducer kafkaDataProducer;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Consumer<Long, String> consumer;

    public static final String TOPIC = "some-data";

    private SourceData sourceData() {
        return SourceData.builder().id(Long.valueOf(1)).description("test description").completed(true).build();
    }

    @BeforeEach
    void setup() {
        // create the consumer with properties
        Map<String, Object> configs = new HashMap<>(
                KafkaTestUtils.consumerProps("group1", "true", embeddedKafkaBroker));
        consumer = new DefaultKafkaConsumerFactory<>(configs, new LongDeserializer(), new StringDeserializer())
                .createConsumer();
        // subscribe the consumer to the desired topic(s)
        embeddedKafkaBroker.consumeFromEmbeddedTopics(consumer, TOPIC);
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void postDataEvent() throws JsonProcessingException {
        // arrange
        SourceData dataSent = sourceData();

        // act
        kafkaDataProducer.send(dataSent);

        // assert
        ConsumerRecord<Long, String> consumerRecord = KafkaTestUtils.getSingleRecord(consumer, TOPIC);
        SourceData dataReceived = objectMapper.readValue(consumerRecord.value(), SourceData.class);
        assert dataReceived.equals(dataSent);
    }
}
