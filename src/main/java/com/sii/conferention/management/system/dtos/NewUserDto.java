package com.sii.conferention.management.system.dtos;

import com.sii.conferention.management.system.entities.UserEntity;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {
    private String username;
    private String email;

    public UserEntity getUserEntity() {
        return UserEntity.builder()
                         .email(this.email)
                         .username(this.username)
                         .build();
    }
}
