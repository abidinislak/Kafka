package com.kafkadockertest.kasif.api;


import com.kafkadockertest.kasif.dto.KMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/kasifpro")
@RequiredArgsConstructor
public class producerController {

    private String topic="kasif_topic";


    private final KafkaTemplate<String, KMessage> kafkaTemplate;


    @PostMapping
    public void sendMEssage(@RequestBody KMessage kMessage)
    {
        kafkaTemplate.send(topic, UUID.randomUUID().toString(),kMessage);
    }
}
