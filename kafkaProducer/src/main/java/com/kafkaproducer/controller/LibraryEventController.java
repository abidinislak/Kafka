package com.kafkaproducer.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafkaproducer.domain.LibraryEvent;
import com.kafkaproducer.producer.LibraryeventProducer;
import com.kafkaproducer.utils.LiabraryEventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LibraryEventController {


    @Autowired
    LibraryeventProducer libraryeventProducer;

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<com.kafkaproducer.domain.LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {


        libraryEvent.setLiabraryEventType(LiabraryEventType.NEW);

        log.info("before send message");
        libraryeventProducer.sendLibraryevent(libraryEvent);

        log.info("after send message");

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}
