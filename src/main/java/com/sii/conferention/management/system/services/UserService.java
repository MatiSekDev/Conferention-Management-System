package com.sii.conferention.management.system.services;

import com.sii.conferention.management.system.configurations.UtilsConfiguration;
import com.sii.conferention.management.system.dtos.UserDataDto;
import com.sii.conferention.management.system.entities.LectureEntity;
import com.sii.conferention.management.system.entities.RoleEntity;
import com.sii.conferention.management.system.entities.UserEntity;
import com.sii.conferention.management.system.enums.RoleEnum;
import com.sii.conferention.management.system.repositories.LectureRepository;
import com.sii.conferention.management.system.repositories.RoleRepository;
import com.sii.conferention.management.system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Optional<UserEntity> getUserByUserData(UserDataDto userDataDto) {
        return userRepository.findByUsernameAndEmail(userDataDto.getUsername(), userDataDto.getEmail());
    }

    public Optional<UserEntity> getUserByUserLogin(String username) {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<String> cancelUserPartakeInLecture(UserDataDto oldUserData, Long lectureId) {
        Optional<UserEntity> existingUser = getUserByUserData(oldUserData);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UtilsConfiguration.USER_DOES_NOT_EXIST);
        }
        Optional<LectureEntity> existingLectureUserWannaQuit = lectureRepository.findLectureById(lectureId);
        if (existingLectureUserWannaQuit.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UtilsConfiguration.LECTURE_DOES_NOT_EXIST);
        }
        if (!participantService.removeUserFromLecture(existingUser.get(), existingLectureUserWannaQuit.get())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(UtilsConfiguration.PARTICiPANT_LECTURE_NOT_QUIT_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UtilsConfiguration.PARTICiPANT_LECTURE_QUIT_MESSAGE);
    }

    public ResponseEntity<String> registerNewUser(UserDataDto newUserData) {
        if (isUserLoginTaken(newUserData)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(UtilsConfiguration.USER_LOGIN_ALREADY_TAKEN);
        }
        if (doesUserAlreadyExist(newUserData)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(UtilsConfiguration.USER_ALREADY_EXIST_MESSAGE);
        }

        Optional<Long> userId = Optional.ofNullable(
                saveUser(newUserData.getUserEntity(), RoleEnum.USER).getId()
        );

        if (userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UtilsConfiguration.USER_ADD_FAILURE_MESSAGE);
        }
        return ResponseEntity.ok(UtilsConfiguration.USER_ADD_SUCCESS_MESSAGE);
    }

    public boolean isUserLoginTaken(UserDataDto userDataDto) {
        return userRepository.findByUsername(userDataDto.getUsername())
                             .filter(user -> !user.getEmail().equals(userDataDto.getEmail()))
                             .isPresent();
    }

    public boolean doesUserAlreadyExist(UserDataDto userDataDto) {
        return userRepository.findByUsernameAndEmail(userDataDto.getUsername(), userDataDto.getEmail())
                             .isPresent();
    }
    public UserEntity saveUser(UserEntity user, RoleEnum role) {
        Optional<RoleEntity> existingRole = roleRepository.findRoleByName(role);
        existingRole.ifPresent(
                roleEntity -> {
                    user.getRoles().add(roleEntity);
                }
        );

        return userRepository.save(user);
    }
}
