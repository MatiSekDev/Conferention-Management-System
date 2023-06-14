package com.sii.conferention.management.system.controllers;

import com.sii.conferention.management.system.configurations.UtilsConfiguration;
import com.sii.conferention.management.system.dtos.NewUserDto;
import com.sii.conferention.management.system.entities.UserEntity;
import com.sii.conferention.management.system.enums.RoleEnum;
import com.sii.conferention.management.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<String> registerNewUser(@RequestBody NewUserDto newUserData) {
        if (userService.isUserLoginTaken(newUserData)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(UtilsConfiguration.USER_LOGIN_ALREADY_TAKEN);
        }
        if (userService.doesUserAlreadyExist(newUserData)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(UtilsConfiguration.USER_ALREADY_EXIST_MESSAGE);
        }
        Optional<Long> userId = Optional.ofNullable(
                userService.saveUser(newUserData.getUserEntity(), RoleEnum.USER).getId()
        );

        if (userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UtilsConfiguration.USER_ADD_FAILURE_MESSAGE);
        }

        return ResponseEntity.ok(UtilsConfiguration.USER_ADD_SUCCESS_MESSAGE);
    }
}
