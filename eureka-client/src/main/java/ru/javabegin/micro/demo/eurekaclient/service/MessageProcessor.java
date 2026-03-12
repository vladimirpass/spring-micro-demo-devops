package ru.javabegin.micro.demo.eurekaclient.service;


import org.springframework.stereotype.Service;
import ru.javabegin.micro.demo.eurekaclient.dto.Response;
import ru.javabegin.micro.demo.eurekaclient.dto.UserNotificationDto;
import ru.javabegin.micro.demo.eurekaclient.kafka.KafkaMessageConsumer;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageProcessor {

    public List<Response> sendUserMessage(){
        List<UserNotificationDto> userNotificationDtos = getAllMessages();
        List<Response> list = new ArrayList<>();

        for(UserNotificationDto userNotificationDto : userNotificationDtos){
            System.out.println(userNotificationDto.toString());
        }

        for (UserNotificationDto userNotificationDto : userNotificationDtos) {
            if(userNotificationDto.statusType().equals("CREATED")) list.add(new Response(userNotificationDto.email()
                    + " Здравствуйте! Ваш аккаунт на сайте был успешно создан"));
            if(userNotificationDto.statusType().equals("DELETED")) list.add(new Response(userNotificationDto.email()
                    + " Здравствуйте! Ваш аккаунт был удален"));
        }
        return list;
    }

    public List<UserNotificationDto> getAllMessages(){
        KafkaMessageConsumer kafkaMessageConsumer = new KafkaMessageConsumer();
        return kafkaMessageConsumer.getAllMessages();
    }

}