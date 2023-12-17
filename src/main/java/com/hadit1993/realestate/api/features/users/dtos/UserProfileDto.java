package com.hadit1993.realestate.api.features.users.dtos;

import java.util.Date;

public record UserProfileDto(
        String username,
        String email,
        String photo,
        Date creationDate,
        Date lastModifiedDate
) {
}
