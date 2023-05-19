package com.eventconsumer.eventconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryeventConsumer {


    @KafkaListener(topics = {"library-events"},groupId="library-events-listener-group" )
    public void onMessage(ConsumerRecord<String,String>  consumerRecord ){

        log.info("consemerRecord : {}",consumerRecord);


    }
}
