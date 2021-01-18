package com.aagnone3.springkafka.producer.service;

import com.aagnone3.springkafka.producer.domain.SourceData;
import com.aagnone3.springkafka.producer.producers.IDataProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    @Autowired
    @Qualifier("kafkaDataProducer")
    IDataProducer dataProducer;

	public SourceData send(SourceData data) throws Exception {
        dataProducer.send(data);
        return data;
	}

}
