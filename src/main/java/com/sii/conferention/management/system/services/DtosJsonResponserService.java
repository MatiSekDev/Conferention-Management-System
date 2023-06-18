package com.sii.conferention.management.system.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sii.conferention.management.system.configurations.UtilsConfiguration;
import com.sii.conferention.management.system.dtos.ConferencePlanDto;
import com.sii.conferention.management.system.dtos.LecturePercentageAttendanceDto;
import com.sii.conferention.management.system.dtos.LectureTopicTypeAttendanceDto;
import com.sii.conferention.management.system.entities.ParticipantEntity;
import com.sii.conferention.management.system.enums.TopicTypeEnum;
import com.sii.conferention.management.system.repositories.LectureRepository;
import com.sii.conferention.management.system.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

@Service
public class DtosJsonResponserService {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    public String getLectureTopicTypeAttendance() {
        List<Object[]> lectureTopicTypeAttendanceListPreParsed = lectureRepository.countParticipantsByLectureTopicType();
        List<LectureTopicTypeAttendanceDto> lectureTopicTypeAttendanceList = new LinkedList<>();
        for (Object[] row : lectureTopicTypeAttendanceListPreParsed) {
            lectureTopicTypeAttendanceList.add(
                       LectureTopicTypeAttendanceDto.builder()
                                                    .lectureTopicType((TopicTypeEnum) row[0])
                                                    .sumOfAttendee((Long) row[1])
                                                    .build()
            );
        }
        try {
            return new ObjectMapper().writeValueAsString(lectureTopicTypeAttendanceList);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return UtilsConfiguration.JSON_PARSING_EXCEPTION_MESSAGE_ENGLISH;
        }
    }

    public String getLecturePercentageAttendance() {
        LinkedList<LecturePercentageAttendanceDto> lecturePercentageAttendanceList = new LinkedList<>();
        lectureRepository.findAll().forEach(
                lecture -> {
                    lecturePercentageAttendanceList.add(
                            LecturePercentageAttendanceDto.builder()
                                    .conferenceIdentifier(lecture.getId())
                                    .percentageAttendance(
                                            (participantRepository.countParticipantsByLecture(lecture.getId()) * 100)
                                            / lecture.getMaxNumberOfParticipants()
                                    )
                                    .endTime(lecture.getEndTime())
                                    .startTime(lecture.getStartTime())
                                    .lectureTopicType(lecture.getTopicType())
                                    .build()
                    );

                }
        );
        try {
            return new ObjectMapper().writeValueAsString(lecturePercentageAttendanceList);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return UtilsConfiguration.JSON_PARSING_EXCEPTION_MESSAGE_ENGLISH;
        }
    }

    public String getConferencePlan() {
        LinkedList<ConferencePlanDto> conferencePlan = new LinkedList<>();
        lectureRepository.findAll().forEach(
                lecture -> {
                    conferencePlan.add(
                            ConferencePlanDto.builder()
                                    .conferenceIdentifier(lecture.getId())
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
