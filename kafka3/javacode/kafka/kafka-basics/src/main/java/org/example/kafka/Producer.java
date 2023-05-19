package org.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Properties;

public class Producer {


    private static final Logger log= LoggerFactory.getLogger(Producer.class.getSimpleName());
    public static void main(String[] args) {
log.info("Let's start...");

        Properties properties=new Properties();
        properties.setProperty("bootstrap.servers","127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());


        KafkaProducer<String,String> producer=new KafkaProducer<>(properties);

        ProducerRecord<String,String> producerRecord=new ProducerRecord<>("demo_java","new valueee");

        producer.send(producerRecord);
        producer.flush();
        producer.close();

    }
}
