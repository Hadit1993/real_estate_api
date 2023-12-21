package com.hadit1993.realestate.api.features.authentication.services;

import com.hadit1993.realestate.api.features.authentication.dtos.GoogleAuthDto;
import com.hadit1993.realestate.api.features.authentication.dtos.LoginUserDto;
import com.hadit1993.realestate.api.features.authentication.dtos.RegisterUserDto;
import com.hadit1993.realestate.api.features.users.dtos.UserProfileDto;
import com.hadit1993.realestate.api.features.users.entities.User;
import com.hadit1993.realestate.api.features.users.repositories.UserRepository;
import com.hadit1993.realestate.api.security.jwt.JWTService;
import com.hadit1993.realestate.api.utils.UniqueStringGenerator;
import com.hadit1993.realestate.api.utils.exceptions.BadRequestException;
import com.hadit1993.realestate.api.utils.exceptions.UnAuthorizedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationServiceV1 {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;

    private final JwtDecoder jwtDecoder;

    private final JWTService jwtService;

    public AuthenticationServiceV1(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationProvider authenticationProvider,
            JwtDecoder jwtDecoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.jwtDecoder = jwtDecoder;
        this.jwtService = jwtService;
    }

    public void signup(RegisterUserDto userDto) {

        final User existingUserByEmail = userRepository.findByEmail(userDto.email());

        if (existingUserByEmail != null)
            throw new BadRequestException(Map.of("email", "A user with this email already exists"));

        User existingUserByUsername = userRepository.findByUsername(userDto.username());

        if (existingUserByUsername != null) {
            throw new BadRequestException(Map.of("username", "A user with this username already exists"));
        }

        String hashedPassword = passwordEncoder.encode(userDto.password());

        User newUser = new User(userDto.username(), userDto.email(), hashedPassword);

        userRepository.save(newUser);

    }

    public Map<String, Object> signin(LoginUserDto userDto) {
        final User user = userRepository.findByEmail(userDto.email());
        if (user == null) throw new UnAuthorizedException("Bad credentials");
        return authenticate(user, userDto.password());
    }

    private Map<String, Object> authenticate(User user, String password) {
        final Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken(user.getUserId().toString(),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        return Map.of("token", token, "user", new UserProfileDto(
                user.getUsername(),
                user.getEmail(),
                user.getPhoto(),
                user.getCreationDate(),
                user.getLastModifiedDate()));
    }



    public Map<String, Object> signinGoogle(GoogleAuthDto googleAuthDto) {
        try {
            Jwt jwt = jwtDecoder.decode(googleAuthDto.idToken());
            String subject = jwt.getSubject();
            final User existingUser = userRepository.findByEmail(googleAuthDto.email());
            if(existingUser != null) {
                if (!existingUser.isGoogleAccount()) {
                    throw new BadRequestException(Map.of("email", "A user with this email already exists"));
                }

                return signin(new LoginUserDto(googleAuthDto.email(), subject));
            }

            String hashedPassword = passwordEncoder.encode(subject);

            String givenUsername = googleAuthDto.username().toLowerCase().replaceAll(" ", "");

            String username = givenUsername.length() > 15 ? givenUsername.substring(0,14) : givenUsername + UniqueStringGenerator.generateUniqueString(15 - givenUsername.length());

            User newUser = new User(username, googleAuthDto.email(), hashedPassword);
            newUser.setGoogleAccount(true);
            newUser.setPhoto(googleAuthDto.photo());

            final User savedUser = userRepository.save(newUser);

            return authenticate(savedUser, subject);


        } catch (JwtException e) {
            throw new UnAuthorizedException("idToken is invalid");
        }

    }

}
