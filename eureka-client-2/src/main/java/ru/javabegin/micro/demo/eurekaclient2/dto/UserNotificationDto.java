package ru.javabegin.micro.demo.eurekaclient2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationDto {
    private String userId;
    private String email;
    private String statusType;
}
