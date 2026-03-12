package ru.javabegin.micro.demo.eurekaclient2.kafka;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.javabegin.micro.demo.eurekaclient2.dto.UserNotificationDto;

@Service
public class UserKafkaProducer {

    private Logger log = LoggerFactory.getLogger(UserKafkaProducer.class);


    private KafkaTemplate<String, UserNotificationDto> kafkaTemplate;

    public UserKafkaProducer(KafkaTemplate<String, UserNotificationDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @CircuitBreaker(name = "kafkaProducer", fallbackMethod = "sendToKafkaFallback")
    public void sendOrderToKafka(UserNotificationDto userNotificationDto){
        kafkaTemplate.send("users", userNotificationDto.getUserId(), userNotificationDto);
        log.info("Order sent to kafka: id={}", userNotificationDto.getUserId());

    }

    // Fallback метод (обязательно должен быть в том же классе)
    public void sendToKafkaFallback(UserNotificationDto userNotificationDto, Exception e) {
        log.error("Failed to send to Kafka, using fallback: {}", userNotificationDto.getUserId(), e);
        // Здесь можно просто логировать или сохранять в файл
        System.err.println("Сообщение не отправлено в Kafka: " + userNotificationDto.getUserId());
    }

}