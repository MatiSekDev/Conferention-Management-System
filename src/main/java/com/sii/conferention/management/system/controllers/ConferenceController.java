package com.sii.conferention.management.system.controllers;

import com.sii.conferention.management.system.dtos.UserDataDto;
import com.sii.conferention.management.system.services.LectureService;
import com.sii.conferention.management.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conference")
public class ConferenceController {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private UserService userService;

    @GetMapping("/plan")
    public String getConferencePlan() {
        return lectureService.getConferencePlan();
    }

    @PostMapping("/lecture/join/{lectureId}")
    public ResponseEntity<String> joinLecture(@RequestBody UserDataDto userData, @PathVariable Long lectureId) {
        return lectureService.assignUserToLectureByUserDataAndLectureId(userData, lectureId);
    }
}
