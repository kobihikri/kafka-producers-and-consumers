import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Main {
    public static void main(String... args){

        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        producerProperties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducer");
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Producer<String, String> kafkaProducer = new KafkaProducer<>(producerProperties);

// 1. Produce to topic - let the DEFAULT PARTITIONER decide which partition to write the message to
        ProducerRecord<String, String> message
                = new ProducerRecord("test-topic","message-key","message-value");

// 2. Produce to a specific topic partition
//        int partitionNumber = 1;
//        message = new ProducerRecord("test-topic",partitionNumber, "message-key","message-value");

        try{
            System.out.println("Producing message");
            RecordMetadata recordMetadata = kafkaProducer.send(message).get();
            System.out.println("Message produced: recordMetadata = partition:" + recordMetadata.partition() + ", offset:" + recordMetadata.offset());
        } catch (Exception ex){
            System.out.println("Error producing message: exception = " + ex.getMessage());
        }finally {
            kafkaProducer.flush();
            kafkaProducer.close();
            System.out.println("Producer flushed and closed");
        }
    }
}
