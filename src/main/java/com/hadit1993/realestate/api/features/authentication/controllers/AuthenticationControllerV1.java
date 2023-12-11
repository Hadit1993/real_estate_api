package com.hadit1993.realestate.api.features.authentication.controllers;


import com.hadit1993.realestate.api.features.authentication.dtos.RegisterUserDto;
import com.hadit1993.realestate.api.features.authentication.services.AuthenticationServiceV1;
import com.hadit1993.realestate.api.utils.templetes.ResponseTemplate;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationControllerV1 {

    private final AuthenticationServiceV1 authenticationService;

    public AuthenticationControllerV1(AuthenticationServiceV1 authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/signup")
    private ResponseEntity<ResponseTemplate<Void>> signup(@RequestBody @Valid RegisterUserDto userDto) {

        authenticationService.signup(userDto);

        return ResponseTemplate.<Void>builder().message("User successfully registered").build().convertToResponse(HttpStatus.CREATED);


    }
}
