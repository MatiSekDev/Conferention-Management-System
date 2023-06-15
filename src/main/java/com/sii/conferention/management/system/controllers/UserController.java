package com.sii.conferention.management.system.controllers;

import com.sii.conferention.management.system.configurations.UtilsConfiguration;
import com.sii.conferention.management.system.dtos.UserDataDto;
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
    public ResponseEntity<String> registerNewUser(@RequestBody UserDataDto newUserData) {
        return userService.registerNewUser(newUserData);
    }
}
