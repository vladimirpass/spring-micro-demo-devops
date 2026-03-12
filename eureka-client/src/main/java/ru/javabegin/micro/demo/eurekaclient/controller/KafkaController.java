package ru.javabegin.micro.demo.eurekaclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javabegin.micro.demo.eurekaclient.dto.Response;
import ru.javabegin.micro.demo.eurekaclient.dto.UserNotificationDto;
import ru.javabegin.micro.demo.eurekaclient.service.MessageProcessor;

import java.util.List;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final MessageProcessor messageProcessor;

    public KafkaController(MessageProcessor messageProcessor){
        this.messageProcessor = messageProcessor;
    }

    @GetMapping("/messages")
    public List<UserNotificationDto> getAll(){
        return messageProcessor.getAllMessages();
    }

    @GetMapping("/sendMessagesUsers")
    public List<Response> sendMessagesAllUsers(){
        return messageProcessor.sendUserMessage();
    }


}