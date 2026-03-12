package ru.javabegin.micro.demo.eurekaclient2.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.javabegin.micro.demo.eurekaclient2.dto.Response;
import ru.javabegin.micro.demo.eurekaclient2.dto.UserDto;
import ru.javabegin.micro.demo.eurekaclient2.dto.UserNotificationDto;
import ru.javabegin.micro.demo.eurekaclient2.dto.UserResponseDto;
import ru.javabegin.micro.demo.eurekaclient2.kafka.UserKafkaProducer;
import ru.javabegin.micro.demo.eurekaclient2.model.User;
import ru.javabegin.micro.demo.eurekaclient2.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCRUDService implements CRUDServiceUser<UserDto>{

    private final UserRepository userRepository;
    private final UserKafkaProducer userKafkaProducer;

    @Override
    public UserResponseDto getById(Integer id) {
        try{
            User user = userRepository.findById(id).orElseThrow();
            UserDto userDto = mapToDto(user);
            return new UserResponseDto(0,userDto,null);
        }catch(Exception e){
            return new UserResponseDto(1,null, List.of(e.toString()));
        }

    }

    @Override
    public Collection<UserResponseDto> getAll() {
        try{
            return userRepository.findAll().stream()
                    .map(user -> {
                        UserDto userDto = mapToDto(user);
                        return new UserResponseDto(0, userDto,null);
                    })
                    .toList();
        }catch (Exception e){
            return Collections.emptyList();
        }

    }

    @Override
    public Response create(UserDto userDto) {
        try{
            if(userDto.getNameUser() != null && userDto.getEmail() != null &&
                    userDto.getAge() != null){
                log.info("Create");
                User user = userRepository.save(mapToEntity(userDto));

                UserNotificationDto userNotificationDto = new UserNotificationDto(
                        Integer.toString(user.getId()),
                        userDto.getEmail(),
                        "CREATED"
                );
                userKafkaProducer.sendOrderToKafka(userNotificationDto);


                return new Response(0,"Пользователь сохранен",null);
            }
        }catch (Exception e){
            return new Response(1,
                    "Произошла ошибка при попытке сохранения пользователя",
                    List.of(e.toString()));
        }
        return new Response(1,
                "Произошла ошибка при попытке сохранения пользователя",
                List.of("Не все данные были заполнены"));
    }

    @Override
    public Response update(Integer id, UserDto userDto) {
        try {
            log.info("Update");
            userDto.setId(id);
            userRepository.save(mapToEntity(userDto));
            return new Response(0,"Пользователь обновлен",null);
        }catch (Exception e){
            return new Response(1,
                    "Произошла ошибка при попытке обновления пользователя",
                    List.of(e.toString()));
        }
    }

    @Override
    public Response delete(Integer id) {
        try {
            if(userRepository.findById(id).isPresent()){
                log.info("Delete");
                User user = userRepository.findById(id).orElseThrow();
                userRepository.deleteById(id);

                UserNotificationDto userNotificationDto = new UserNotificationDto(
                        Integer.toString(id),
                        user.getEmail(),
                        "DELETED"
                );
                userKafkaProducer.sendOrderToKafka(userNotificationDto);


                return new Response(0,"Пользователь удален", null);
            }
        }catch (Exception e){
            return new Response(1,
                    "Прогзошла ошибка пр попытке удаления пользователя",
                    List.of(e.toString()));
        }
        return new Response(1,
                "Удаление пользователя не возможно",
                List.of("Пользователя с таким id не существует"));
    }


    public static User mapToEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setNameUser(userDto.getNameUser());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        user.setCreateAt(userDto.getCreateAt());
        return user;
    }

    public static UserDto mapToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setNameUser(user.getNameUser());
        userDto.setEmail(user.getEmail());
        userDto.setAge(user.getAge());
        userDto.setCreateAt(user.getCreateAt());
        return userDto;
    }
}
