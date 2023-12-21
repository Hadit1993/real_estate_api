package com.hadit1993.realestate.api.features.users;

import com.hadit1993.realestate.api.features.users.dtos.UserProfileDto;
import com.hadit1993.realestate.api.features.users.dtos.UserUpdateDto;
import com.hadit1993.realestate.api.features.users.entities.User;
import com.hadit1993.realestate.api.features.users.services.UsersServiceV1;
import com.hadit1993.realestate.api.utils.templetes.ResponseTemplate;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UsersControllerV1 {

    private final UsersServiceV1 usersServiceV1;

    public UsersControllerV1(UsersServiceV1 usersServiceV1) {
        this.usersServiceV1 = usersServiceV1;
    }


    @PutMapping("/profile")
    public ResponseEntity<ResponseTemplate<UserProfileDto>> updateUser(@RequestBody @Valid UserUpdateDto userUpdateDto, Authentication authentication) {

        final User user = usersServiceV1.updateUser(authentication.getName(), userUpdateDto);

        return ResponseTemplate
                .<UserProfileDto>builder()
                .data(new UserProfileDto(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhoto(),
                        user.getCreationDate(),
                        user.getLastModifiedDate()))
                .build()
                .convertToResponse();

    }
}
