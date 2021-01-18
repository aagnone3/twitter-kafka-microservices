package com.aagnone3.springkafka.producer.producers;

import com.aagnone3.springkafka.producer.domain.SourceData;

public interface IDataProducer {
    public Object send(SourceData data) throws Exception;    
}
