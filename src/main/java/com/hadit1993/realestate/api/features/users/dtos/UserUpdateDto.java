package com.hadit1993.realestate.api.features.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(

        @Size(min = 3, max = 15, message = "username must be between 3 and 15 characters long")
        String username,
        @Email(message = "email is invalid")
        String email,
        String photo) {
}
