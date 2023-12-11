package com.hadit1993.realestate.api.features.authentication.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserDto(
        @NotBlank(message = "email is required")
        @Email(message = "email is invalid")
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 6, max = 16, message = "password must be between 6 and 16 characters long")
        String password
) {}
