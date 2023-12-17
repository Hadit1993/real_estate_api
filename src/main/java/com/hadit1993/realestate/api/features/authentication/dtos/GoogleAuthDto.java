package com.hadit1993.realestate.api.features.authentication.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GoogleAuthDto(
        @NotBlank(message = "email is required")
        @Email(message = "email is invalid")
        String email,
        @NotBlank(message = "username is required")
        @Size(min = 3, max = 15, message = "username must be between 3 and 15 characters long")
        String username,
        @NotBlank(message = "username is required")
        @Pattern(regexp = "^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=]+)?\\.[A-Za-z0-9-_.+/=]+$",
                message = "idToken is not a valid jwt token")
        String idToken,
        String photo
) {}
