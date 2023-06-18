package com.sii.conferention.management.system.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sii.conferention.management.system.configurations.UtilsConfiguration;
import com.sii.conferention.management.system.dtos.UserDataDto;
import com.sii.conferention.management.system.dtos.UserLecturesDto;
import com.sii.conferention.management.system.entities.LectureEntity;
import com.sii.conferention.management.system.entities.UserEntity;
import com.sii.conferention.management.system.enums.OrganiserDataRequestTypeEnum;
import com.sii.conferention.management.system.enums.RoleEnum;
import com.sii.conferention.management.system.repositories.LectureRepository;
import com.sii.conferention.management.system.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {

    @Autowired
    private DtosJsonResponserService dtosJsonResponserService;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private LoggingService loggingService;
    @Autowired
    private ParticipantService participantService;

    public ResponseEntity<String> showJoinedLectures(String userLogin) {
        Optional<UserEntity> existingUser = userService.getUserByUserLogin(userLogin);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UtilsConfiguration.USER_DOES_NOT_EXIST);
        }

        List<Long> userLecturesIds = participantRepository.findLecturesIdsUserIsAssignedTo(existingUser.get().getId());
        List<LectureEntity> userLectures = lectureRepository.findLecturesByLecturesId(userLecturesIds);

        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    new ObjectMapper().writeValueAsString(
                            UserLecturesDto.getListOfUserLecturesDtosFromListOdLectureEntities(userLectures)
                    )
            );
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(UtilsConfiguration.JSON_PARSING_EXCEPTION_MESSAGE_ENGLISH);
        }
    }
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
        loggingService.writeToEmailFileLog(loggingService.parseToMessage(userData.getEmail(), UtilsConfiguration.EMAIL_NOTIFICATION_MESSAGE));
        return ResponseEntity.ok(UtilsConfiguration.LECTURE_JOINED_SUCCESS_MESSAGE);
    }



    private boolean isTimeOfLectureInConflictForUser(LectureEntity lectureAlreadyJoined, LectureEntity lectureToJoin) {
        if (lectureAlreadyJoined.getStartTime().toString().equals(lectureToJoin.getStartTime().toString()) &&
                lectureAlreadyJoined.getEndTime().toString().equals(lectureToJoin.getEndTime().toString())) {
            return true;
        }
        return false;
    }

    public ResponseEntity<String> getStatisticDataForOrganiser(UserDataDto userDataDto, OrganiserDataRequestTypeEnum dataRequestType) {
        Optional<UserEntity> existingUser = userService.getUserByUserData(userDataDto);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UtilsConfiguration.USER_DOES_NOT_EXIST);
        }
        if (existingUser.get().getRoles().stream().noneMatch(role -> role.getName().equals(RoleEnum.ORGANIZER))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(UtilsConfiguration.USER_IS_NOT_ORGANISER);
        }

        return switch (dataRequestType) {
            case BY_TOPIC_TYPE ->
                    ResponseEntity.status(HttpStatus.OK).body(dtosJsonResponserService.getLectureTopicTypeAttendance());
            case BY_FULLNESS_PERCENTAGE ->
                    ResponseEntity.status(HttpStatus.OK).body(dtosJsonResponserService.getLecturePercentageAttendance());
        };
    }

    public ResponseEntity<String> getConferencePlan() {
        return ResponseEntity.status(HttpStatus.OK).body(dtosJsonResponserService.getConferencePlan());
    }
}
