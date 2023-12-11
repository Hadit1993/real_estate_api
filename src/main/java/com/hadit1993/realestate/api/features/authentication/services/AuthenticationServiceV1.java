package com.hadit1993.realestate.api.features.authentication.services;

import com.hadit1993.realestate.api.features.authentication.dtos.RegisterUserDto;
import com.hadit1993.realestate.api.features.users.entities.User;
import com.hadit1993.realestate.api.features.users.repositories.UserRepository;
import com.hadit1993.realestate.api.utils.exceptions.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceV1 {

     private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceV1(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(RegisterUserDto userDto) {

        final User existingUserByEmail = userRepository.findByEmail(userDto.email());

        if(existingUserByEmail != null) throw new BadRequestException("A user with this email already exists", null);

        User existingUserByUsername = userRepository.findByUsername(userDto.username());

        String hashedPassword = passwordEncoder.encode(userDto.password());

        if (existingUserByUsername != null) {
            throw new BadRequestException("A user with this username already exists", null);
        }

        User newUser = new User(userDto.username(), userDto.email(), hashedPassword);

        userRepository.save(newUser);
    }
}
