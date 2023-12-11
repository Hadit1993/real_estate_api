package com.hadit1993.realestate.api.features.authentication.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserDto(
        @NotBlank(message = "email is required")
        @Email(message = "email is invalid")
        String email,
        @NotBlank(message = "username is required")
        @Size(min = 3, max = 15, message = "username must be between 3 and 15 characters long")
        String username,

        @NotBlank(message = "password is required")
        @Size(min = 6, max = 16, message = "password must be between 6 and 16 characters long")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).*$",
                message = "password must meet the constraints")
        String password) {}


