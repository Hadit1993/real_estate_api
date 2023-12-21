package com.hadit1993.realestate.api.features.users.services;

import com.hadit1993.realestate.api.features.users.dtos.UserUpdateDto;
import com.hadit1993.realestate.api.features.users.entities.User;
import com.hadit1993.realestate.api.features.users.repositories.UserRepository;
import com.hadit1993.realestate.api.utils.exceptions.BadRequestException;
import com.hadit1993.realestate.api.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class UsersServiceV1 {

    private final UserRepository userRepository;

    public UsersServiceV1(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) throw new NotFoundException("user not found with this email");
        return  user;
    }

    public User updateUser(String email, UserUpdateDto userUpdateDto) {

        User user = findUserByEmail(email);

        if(userUpdateDto.username() != null) {
            User existingUser = userRepository.findByUsername(userUpdateDto.username());
            if(existingUser == null) {
                user.setUsername(userUpdateDto.username());
            } else if(!Objects.equals(existingUser.getUserId(), user.getUserId())) {
                throw new BadRequestException(Map.of("username", "a user already registered with this username"));
            }

        }

        if(userUpdateDto.email() != null && !userUpdateDto.email().equals(email)) {
            User existingUser = userRepository.findByEmail(userUpdateDto.email());
            if(existingUser == null) {
                user.setEmail(userUpdateDto.email());
            } else {
                throw new BadRequestException(Map.of("email", "a user already registered with this email"));
            }
        }

        if(userUpdateDto.photo() != null) {
            user.setPhoto(userUpdateDto.photo());
        }


        return userRepository.save(user);

    }

}
