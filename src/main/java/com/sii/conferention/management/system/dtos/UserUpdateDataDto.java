package com.sii.conferention.management.system.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sii.conferention.management.system.entities.UserEntity;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDataDto {
    private String username;
    private String email;
    private String newEmail;

    public UserDataDto getUserDataDto() {
        return UserDataDto.builder()
                          .email(email)
                          .username(username)
                          .build();
    }
}
