package ru.javabegin.micro.demo.eurekaclient.dto;

public record UserNotificationDto(
        String userId,
        String email,
        String statusType
) {}
