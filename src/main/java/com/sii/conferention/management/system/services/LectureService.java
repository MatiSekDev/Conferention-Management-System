package com.sii.conferention.management.system.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sii.conferention.management.system.configurations.UtilsConfiguration;
import com.sii.conferention.management.system.dtos.ConferencePlanDto;
import com.sii.conferention.management.system.entities.ParticipantEntity;
import com.sii.conferention.management.system.repositories.LectureRepository;
import com.sii.conferention.management.system.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.stream.Stream;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    public String getConferencePlan() {
        LinkedList<ConferencePlanDto> conferencePlan = new LinkedList<>();
        lectureRepository.findAll().forEach(
                lecture -> {
                    conferencePlan.add(
                            ConferencePlanDto.builder()
                                    .maxNumberOfParticipants(lecture.getMaxNumberOfParticipants())
                                    .currentParticipantsNumber(
                                            participantRepository.countParticipantsByLecture(lecture.getId())
                                    )
                                    .endTime(lecture.getEndTime())
                                    .startTime(lecture.getStartTime())
                                    .lectureTopicType(lecture.getTopicType())
                                    .build()
                    );

                }
        );

        try {
            return new ObjectMapper().writeValueAsString(conferencePlan);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return UtilsConfiguration.JSON_PARSING_EXCEPTION_MESSAGE_ENGLISH;
        }
    }

}
