package com.sii.conferention.management.system.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sii.conferention.management.system.services.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conference")
public class ConferenceController {

    @Autowired
    private LectureService lectureService;

    @GetMapping("/plan")
    public String getConferencePlan() {
        return lectureService.getConferencePlan();
    }
}
