import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

public class Main {
    public static void main(String... args){

        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducer");
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Producer<String, String> kafkaProducer = new KafkaProducer<>(producerProperties);

        ProducerRecord<String, String> message
                = new ProducerRecord("destination-topic-name","message-key","message-value");

        try{
            System.out.println("Producing message");
            Future<RecordMetadata> sendFuture = kafkaProducer.send(message);
            kafkaProducer.flush();
            RecordMetadata recordMetadata = sendFuture.get();
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
