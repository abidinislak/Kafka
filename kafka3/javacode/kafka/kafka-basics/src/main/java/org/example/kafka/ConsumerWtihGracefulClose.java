package org.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerWtihGracefulClose {


    private static final Logger log = LoggerFactory.getLogger(ConsumerWtihGracefulClose.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Let's start CONSUME!!!...");

        String groupid = "my_jav_application";
        String topic = "demo_java";


        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");


        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", groupid);
//        properties.setProperty("auto.offset.reset","none/earliest/latest");
        properties.setProperty("auto.offset.reset","earliest");


        KafkaConsumer<String,String> kafkaConsumer=new KafkaConsumer<>(properties);


        final Thread mainThread=Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {

                log.info("detecting a shutdown, lets exit by calling consumer.wakeup()....");

                kafkaConsumer.wakeup();

                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            }
        });


        try {


        kafkaConsumer.subscribe(Arrays.asList(topic));


        while (true)
        {
            log.info("consuming....");
            ConsumerRecords<String,String> recodrs=kafkaConsumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String,String> record:recodrs
                 ) {


                log.info("Key :"+record.key()+" Value : "+record.value());
                log.info("Partion :"+record.partition()+" Offset : "+record.offset());
            }

        }
        }
        catch (WakeupException e)
        {
            log.info("consueör is strting to shutdown");
        }
        catch (Exception e)
        {
            log.error("Uexpeced exception is he comsumer",e);
        }
        finally {
            kafkaConsumer.close();
            log.info("the consumer is now gracefullly shut down");
        }



    }
}
