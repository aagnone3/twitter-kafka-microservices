package com.aagnone3.springkafka.producer.producers;

import java.util.List;

import com.aagnone3.springkafka.producer.domain.SourceData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("kafkaDataProducer")
public class KafkaDataProducer implements IDataProducer {

	@Value("${topic.name}")
    private String topicName;
    
	@Autowired
	private KafkaTemplate<Long, SourceData> kafkaTemplate;

	@Autowired
	ObjectMapper objectMapper;

	public Object send(SourceData data) throws JsonProcessingException {
		// build the record to send
		Long key = data.getId();
		ProducerRecord<Long, SourceData> record = buildRecord(data);

		// send the record and handle futures
		ListenableFuture<SendResult<Long, SourceData>> listenableFuture = kafkaTemplate.send(record);
		listenableFuture.addCallback(
			this::handleSendSuccess,
			exception -> handleSendFailure(key, exception)
		);
		return null;
	}

	private ProducerRecord<Long, SourceData> buildRecord(SourceData data) throws JsonProcessingException {
		List<Header> recordHeaders = List.of(new RecordHeader("event-source", "some-data-producer".getBytes()));
		return new ProducerRecord<Long, SourceData>(topicName, null, data.getId(), data, recordHeaders);
	}

	public void handleSendSuccess(SendResult<Long, SourceData> sendResult) {
		Long keySent = sendResult.getProducerRecord().key();
		log.info("Send successful {}", keySent);
	}

	private void handleSendFailure(Long sendKey, Throwable ex) {
		log.error("Error sending message with key {}: {}", sendKey, ex);
		try {
			throw ex;
		} catch (Throwable newEx) {
			log.error("Error throwing failed message exception: {}", newEx);
		}
	}
    
}
