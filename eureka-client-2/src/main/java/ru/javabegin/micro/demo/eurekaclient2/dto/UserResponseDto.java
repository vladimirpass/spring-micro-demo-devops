package ru.javabegin.micro.demo.eurekaclient2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Integer status;
    private UserDto userDto;
    private List<String> error;
}