package com.kafkaproducer.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaproducer.domain.LibraryEvent;
import jdk.jshell.EvalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class LibraryeventProducer {


    @Autowired
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

public void sendLibraryevent(LibraryEvent libraryEvent) throws JsonProcessingException {
    Integer key=libraryEvent.getLibraryEventid();

    String value=objectMapper.writeValueAsString(libraryEvent);


    ProducerRecord<Integer ,String > producerrecord=buildProducerRescord(key,value,"library-events");




    CompletableFuture<SendResult<Integer, String>> resultCompletableFuture=  kafkaTemplate.send(producerrecord);
//    CompletableFuture<SendResult<Integer, String>> resultCompletableFuture=  kafkaTemplate.send("library-events",key, value);

    resultCompletableFuture.whenComplete((result, ex) -> {
        if (ex == null) {
            System.out.println("Sent message=[" + key +
                    "] with offset=[" + result.getRecordMetadata().offset() + "]");

            log.info("message send with key {} and partion is  {} ",key,result.getRecordMetadata().partition());
        } else {
            System.out.println("Unable to send message=[" +
                    key + "] due to : " + ex.getMessage());
            log.error("message send with key {}",key);

        }
    });

//    listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
//        @Override
//        public void onFailure(Throwable ex) {
//
//            handleFailure(key,value,ex);
//
//
//        }
//
//        @Override
//        public void onSuccess(SendResult<Integer, String> result) {
//
//            handleSucces(key,value,result);
//
//        }
//
//    });




}

    private ProducerRecord<Integer, String> buildProducerRescord(Integer key, String value, String s) {

        List<Header>     recordHeader=List.of(new RecordHeader("event-source","scanner".getBytes()));
    return  new ProducerRecord<>(s,null,key,value,recordHeader);
    }

    private void handleFailure(Integer key, String value, Throwable ex) {
    log.error("Error Sendng the message and the exception is  {}",ex.getMessage());

try {
    throw ex;
}
catch (Throwable throwable){
    log.error("Eroor in onFailure : {}",throwable.getMessage());
}
    }

    private void handleSucces(Integer key, String value, SendResult<Integer, String> result) {

    log.info("message has benn send scuccedfulyy for the key : {} and the valuye is  {} , pation is  {}",key,value,result.getRecordMetadata().partition());
    }

}
