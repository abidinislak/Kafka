package org.example.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerCallBack {


    private static final Logger log= LoggerFactory.getLogger(ProducerCallBack.class.getSimpleName());
    public static void main(String[] args) {
log.info("İt is second turn.....");

        Properties properties=new Properties();
        properties.setProperty("bootstrap.servers","127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        properties.setProperty("batch.size","400");


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                KafkaProducer<String,String> producer=new KafkaProducer<>(properties);

                ProducerRecord<String,String> producerRecord=new ProducerRecord<>("demo_java","İt has benn started.......AAAAAAA"+j);

                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if(exception==null){

                            log.info("Receive metad data with"+metadata.topic()+"..\n..."+metadata.partition()+"...\n.."+metadata.offset());
                        }
                        else {

                            log.warn("Exception"+exception.getMessage());
                        }
                    }
                });



                producer.flush();
                producer.close();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }
}
