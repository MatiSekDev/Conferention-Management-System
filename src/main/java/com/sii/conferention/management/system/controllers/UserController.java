package com.sii.conferention.management.system.controllers;

import com.sii.conferention.management.system.configurations.UtilsConfiguration;
import com.sii.conferention.management.system.dtos.OrganiserDataRequestDto;
import com.sii.conferention.management.system.dtos.UserDataDto;
import com.sii.conferention.management.system.dtos.UserUpdateDataDto;
import com.sii.conferention.management.system.enums.RoleEnum;
import com.sii.conferention.management.system.services.LectureService;
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

    @Autowired
    private LectureService lectureService;

    @PostMapping("/new")
    public ResponseEntity<String> registerNewUser(@RequestBody UserDataDto newUserData) {
        return userService.registerNewUser(newUserData);
    }

    @PutMapping("/old/update")
    public ResponseEntity<String> updateUserEmail(@RequestBody UserUpdateDataDto userDataDto) {
        return userService.updateUserEmail(userDataDto);
    }

    @GetMapping("/admin/get/all")
    public ResponseEntity<String> getAllUserDataForAdmin(@RequestBody UserDataDto adminUserData) {
        return userService.getAllUserDataForAdmin(adminUserData);
    }

    @GetMapping("/organiser/data")
    public ResponseEntity<String> getStatisticDataForOrganiser(@RequestBody OrganiserDataRequestDto organiserDataRequestDto) {
        return lectureService.getStatisticDataForOrganiser(organiserDataRequestDto.getUserDataDto(), organiserDataRequestDto.getDataRequestType());
    }
}
