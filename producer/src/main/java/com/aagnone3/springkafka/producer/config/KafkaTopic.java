package com.aagnone3.springkafka.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class KafkaTopic {
    
	@Value("${topic.name}")
    private String topicName;
    
    @Value("${topic.partitions}")
    private Integer partitions;

    @Value("${topic.replicationFactor}")
    private short replicationFactor;

	@Bean
	public NewTopic newTopic() {
		return new NewTopic(topicName, partitions, replicationFactor);
	}

}
