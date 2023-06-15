package com.sii.conferention.management.system.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sii.conferention.management.system.configurations.UtilsConfiguration;
import com.sii.conferention.management.system.dtos.ConferencePlanDto;
import com.sii.conferention.management.system.dtos.UserDataDto;
import com.sii.conferention.management.system.entities.LectureEntity;
import com.sii.conferention.management.system.entities.ParticipantEntity;
import com.sii.conferention.management.system.entities.UserEntity;
import com.sii.conferention.management.system.enums.RoleEnum;
import com.sii.conferention.management.system.repositories.LectureRepository;
import com.sii.conferention.management.system.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantService participantService;

    public ResponseEntity<String> assignUserToLectureByUserDataAndLectureId(UserDataDto userData, Long lectureId) {
        Optional<UserEntity> existingUser = userService.getUserByUserData(userData);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UtilsConfiguration.USER_DOES_NOT_EXIST);
        }
        Optional<LectureEntity> existingLectureUserWannaJoin = lectureRepository.findLectureById(lectureId);
        if (existingLectureUserWannaJoin.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UtilsConfiguration.LECTURE_DOES_NOT_EXIST);
        }
        if (existingLectureUserWannaJoin.get().getParticipants().size() == existingLectureUserWannaJoin.get().getMaxNumberOfParticipants()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(UtilsConfiguration.PARTICIPANT_ADD_FAILURE_LIMIT_MESSAGE);
        }

        List<Long> lecturesIdsUserIsAssignedTo = participantRepository.findLecturesIdsUserIsAssignedTo(existingUser.get().getId());
        List<LectureEntity> fullDataOfLecturesUserIsAssignedTo = lectureRepository.findLecturesByLecturesId(lecturesIdsUserIsAssignedTo);
        if (lecturesIdsUserIsAssignedTo.contains(existingLectureUserWannaJoin.get().getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(UtilsConfiguration.PARTICIPANT_ADD_FAILURE_ALREADY_JOINED_MESSAGE);
        }
        for (LectureEntity lectureUserIsAssignedTo : fullDataOfLecturesUserIsAssignedTo) {
            if (isTimeOfLectureInConflictForUser(lectureUserIsAssignedTo, existingLectureUserWannaJoin.get())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(UtilsConfiguration.PARTICIPANT_ADD_FAILURE_CONFLICT_MESSAGE);
            }
        }
        Optional<Long> participantId = Optional.ofNullable(
                participantService.assignUserToLecture(existingUser.get(), existingLectureUserWannaJoin.get()).getId()
        );
        if (participantId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UtilsConfiguration.PARTICIPANT_ADD_FAILURE_MESSAGE);
        }
        return ResponseEntity.ok(UtilsConfiguration.LECTURE_JOINED_SUCCESS_MESSAGE);
    }

    public String getConferencePlan() {
        LinkedList<ConferencePlanDto> conferencePlan = new LinkedList<>();
        lectureRepository.findAll().forEach(
                lecture -> {
                    conferencePlan.add(
                            ConferencePlanDto.builder()
                                    .conferenceidentifier(lecture.getId())
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

    private boolean isTimeOfLectureInConflictForUser(LectureEntity lectureAlreadyJoined, LectureEntity lectureToJoin) {
        if (lectureAlreadyJoined.getStartTime().toString().equals(lectureToJoin.getStartTime().toString()) &&
                lectureAlreadyJoined.getEndTime().toString().equals(lectureToJoin.getEndTime().toString())) {
            return true;
        }
        return false;
    }

}
