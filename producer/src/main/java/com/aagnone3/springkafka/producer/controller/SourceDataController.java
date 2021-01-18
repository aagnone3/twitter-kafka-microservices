package com.aagnone3.springkafka.producer.controller;

import javax.validation.Valid;

import com.aagnone3.springkafka.producer.config.JsonProperties;
import com.aagnone3.springkafka.producer.domain.SourceData;
import com.aagnone3.springkafka.producer.service.DataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SourceDataController {

    @Autowired
    DataService dataService;

    @Autowired
    JsonProperties jsonProperties;

    @PostMapping("/data")
    public ResponseEntity<SourceData> createSourceData(@Valid @RequestBody SourceData sourceData) throws Exception {
        dataService.send(sourceData);
        return new ResponseEntity<>(sourceData, HttpStatus.CREATED);
    }

    @GetMapping("/props")
    public ResponseEntity<JsonProperties> getJsonProperties() {
        return new ResponseEntity<>(jsonProperties, HttpStatus.OK);
    }
}
