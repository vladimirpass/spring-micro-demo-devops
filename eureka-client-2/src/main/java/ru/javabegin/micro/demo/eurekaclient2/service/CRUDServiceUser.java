package ru.javabegin.micro.demo.eurekaclient2.service;

import ru.javabegin.micro.demo.eurekaclient2.dto.Response;
import ru.javabegin.micro.demo.eurekaclient2.dto.UserResponseDto;

import java.util.Collection;

public interface CRUDServiceUser<T> {
    UserResponseDto getById(Integer id);
    Collection<UserResponseDto> getAll();
    Response create(T item);
    Response update(Integer id, T item);
    Response delete(Integer id);
}