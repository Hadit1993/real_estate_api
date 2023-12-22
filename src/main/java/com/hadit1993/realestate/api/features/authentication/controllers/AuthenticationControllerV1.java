package com.hadit1993.realestate.api.features.authentication.controllers;


import com.hadit1993.realestate.api.features.authentication.dtos.GoogleAuthDto;
import com.hadit1993.realestate.api.features.authentication.dtos.LoginUserDto;
import com.hadit1993.realestate.api.features.authentication.dtos.RegisterUserDto;
import com.hadit1993.realestate.api.features.authentication.services.AuthenticationServiceV1;
import com.hadit1993.realestate.api.features.users.dtos.UserProfileDto;
import com.hadit1993.realestate.api.utils.templetes.ResponseTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationControllerV1 {

    private final AuthenticationServiceV1 authenticationService;

    public AuthenticationControllerV1(AuthenticationServiceV1 authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/signup")
    public ResponseEntity<ResponseTemplate<Void>> signup(@RequestBody @Valid RegisterUserDto userDto) {

        authenticationService.signup(userDto);

        return ResponseTemplate.<Void>
                        builder()
                .message("User successfully registered")
                .build()
                .convertToResponse(HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseTemplate<UserProfileDto>> signin(
            @RequestBody @Valid LoginUserDto userDto,
            HttpServletResponse response) {

        var result = authenticationService.signin(userDto);
        String token = (String) result.get("token");
        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        UserProfileDto user = (UserProfileDto) result.get("user");
        return ResponseTemplate.<UserProfileDto>builder().data(user).message("You successfully logged in").build().convertToResponse();


    }

    @PostMapping("/google")
    public ResponseEntity<ResponseTemplate<UserProfileDto>> signinWithGoogle(
            @RequestBody @Valid GoogleAuthDto userDto,
            HttpServletResponse response) {

        var result = authenticationService.signinGoogle(userDto);
        String token = (String) result.get("token");
        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        UserProfileDto user = (UserProfileDto) result.get("user");
        return ResponseTemplate.<UserProfileDto>builder().data(user).message("User successfully logged in").build().convertToResponse();


    }


}
