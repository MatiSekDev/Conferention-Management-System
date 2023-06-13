package com.sii.conferention.management.system.configurations;

import com.sii.conferention.management.system.entities.LectureEntity;
import com.sii.conferention.management.system.entities.RoleEntity;
import com.sii.conferention.management.system.entities.UserEntity;
import com.sii.conferention.management.system.enums.RoleEnum;
import com.sii.conferention.management.system.enums.TopicTypeEnum;
import com.sii.conferention.management.system.repositories.LectureRepository;
import com.sii.conferention.management.system.repositories.RoleRepository;
import com.sii.conferention.management.system.repositories.UserRepository;
import com.sii.conferention.management.system.services.TimeService;
import com.sii.conferention.management.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
public class DatabaseBasicDataConfiguration {

    @Autowired
    private UserService userService;

    @Autowired
    private TimeService timeService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Bean("rolesInserter")
    public CommandLineRunner insertAllRoles() {
        return args -> {
            for (RoleEnum role : RoleEnum.values()) {
                roleRepository.save(
                        RoleEntity.builder()
                                  .name(role)
                                  .build()
                );
            }
        };
    }

    @Bean("usersInserter")
    @DependsOn({"rolesInserter"})
    public CommandLineRunner insertBasicUsers() {
        return args -> {
            userService.saveUser(
                    UserEntity.builder()
                              .username(UtilsConfiguration.ADMIN_USERNAME)
                              .email(UtilsConfiguration.ADMIN_EMAIL)
                              .build(),
                    RoleEnum.ADMIN
            );
            userService.saveUser(
                    UserEntity.builder()
                              .username(UtilsConfiguration.ORGANIZER_USERNAME)
                              .email(UtilsConfiguration.ORGANIZER_EMAIL)
                              .build(),
                    RoleEnum.ORGANIZER
            );
        };
    }

    @Bean("lecturesInserter")
    public CommandLineRunner insertBasicLectures() {

        return args -> {
            for (TopicTypeEnum topicType : TopicTypeEnum.values()) {
                lectureRepository.save(
                        LectureEntity.builder()
                                     .topicType(topicType)
                                     .maxNumberOfParticipants(UtilsConfiguration.MAX_NO_PARTICIPANT_CASE_NORMAL)
                                     .startTime(
                                            timeService.getTemporalTime(
                                                    UtilsConfiguration.LECTURE_FIRST_START_HOUR,
                                                    UtilsConfiguration.LECTURE_START_MINUTE
                                            )
                                     )
                                     .endTime(
                                             timeService.getTemporalTime(
                                                     UtilsConfiguration.LECTURE_FIRST_END_HOUR,
                                                     UtilsConfiguration.LECTURE_END_MINUTE
                                             )
                                     )
                                     .build()
                );
                lectureRepository.save(
                        LectureEntity.builder()
                                .topicType(topicType)
                                .maxNumberOfParticipants(UtilsConfiguration.MAX_NO_PARTICIPANT_CASE_NORMAL)
                                .startTime(
                                        timeService.getTemporalTime(
                                                UtilsConfiguration.LECTURE_SECOND_START_HOUR,
                                                UtilsConfiguration.LECTURE_START_MINUTE
                                        )
                                )
                                .endTime(
                                        timeService.getTemporalTime(
                                                UtilsConfiguration.LECTURE_SECOND_END_HOUR,
                                                UtilsConfiguration.LECTURE_END_MINUTE
                                        )
                                )
                                .build()
                );
                lectureRepository.save(
                        LectureEntity.builder()
                                .topicType(topicType)
                                .maxNumberOfParticipants(UtilsConfiguration.MAX_NO_PARTICIPANT_CASE_NORMAL)
                                .startTime(
                                        timeService.getTemporalTime(
                                                UtilsConfiguration.LECTURE_THIRD_START_HOUR,
                                                UtilsConfiguration.LECTURE_START_MINUTE
                                        )
                                )
                                .endTime(
                                        timeService.getTemporalTime(
                                                UtilsConfiguration.LECTURE_THIRD_END_HOUR,
                                                UtilsConfiguration.LECTURE_END_MINUTE
                                        )
                                )
                                .build()
                );
            }
        };
    }

}
