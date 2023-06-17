package com.sii.conferention.management.system.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sii.conferention.management.system.entities.LectureEntity;
import com.sii.conferention.management.system.entities.UserEntity;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDto {
    private String username;
    private String email;

    @JsonIgnore
    public UserEntity getUserEntity() {
        return UserEntity.builder()
                         .email(this.email)
                         .username(this.username)
                         .build();
    }

    public static List<UserDataDto> getListOfUsersLimitedDataFromListOfUserEntities(List<UserEntity> userEntities) {
        List<UserDataDto> parsedList = new LinkedList<UserDataDto>();
        for (UserEntity user : userEntities) {
            parsedList.add(
                   UserDataDto.builder()
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .build()
            );
        }
        return parsedList;
    }
}
