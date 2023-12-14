package com.hadit1993.realestate.api.features.authentication.services;

import com.hadit1993.realestate.api.features.authentication.dtos.LoginUserDto;
import com.hadit1993.realestate.api.features.authentication.dtos.RegisterUserDto;
import com.hadit1993.realestate.api.features.users.dtos.UserProfileDto;
import com.hadit1993.realestate.api.features.users.entities.User;
import com.hadit1993.realestate.api.features.users.repositories.UserRepository;
import com.hadit1993.realestate.api.security.jwt.JWTService;
import com.hadit1993.realestate.api.utils.exceptions.BadRequestException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceV1 {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;

    private final JWTService jwtService;

    public AuthenticationServiceV1(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationProvider authenticationProvider,
            JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.jwtService = jwtService;
    }

    public void signup(RegisterUserDto userDto) {

        final User existingUserByEmail = userRepository.findByEmail(userDto.email());

        if (existingUserByEmail != null)
            throw new BadRequestException(Map.of("email", "A user with this email already exists"));

        User existingUserByUsername = userRepository.findByUsername(userDto.username());

        String hashedPassword = passwordEncoder.encode(userDto.password());

        if (existingUserByUsername != null) {
            throw new BadRequestException(Map.of("username", "A user with this username already exists"));
        }

        User newUser = new User(userDto.username(), userDto.email(), hashedPassword);

        userRepository.save(newUser);

    }

    public Map<String, Object> signin(LoginUserDto userDto) {
        final Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.email(), userDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userRepository.findByEmail(userDto.email());
        String token = jwtService.generateToken(authentication.getName(),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        return Map.of("token", token, "user", new UserProfileDto(
                user.getUsername(),
                user.getEmail(),
                user.getCreationDate(),
                user.getLastModifiedDate()));
    }
}
