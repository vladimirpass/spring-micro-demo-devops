package ru.javabegin.micro.demo.eurekaclient2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import ru.javabegin.micro.demo.eurekaclient2.dto.UserNotificationDto;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, UserNotificationDto> producerFactory(){
        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        JacksonJsonSerializer<UserNotificationDto> serializer = new JacksonJsonSerializer<>();
        serializer.setAddTypeInfo(false);

        return new DefaultKafkaProducerFactory<>(configProperties,
                new StringSerializer(),
                serializer);
    }
    @Bean
    public KafkaTemplate<String, UserNotificationDto> kafkaTemplate(
            ProducerFactory<String, UserNotificationDto> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }
}