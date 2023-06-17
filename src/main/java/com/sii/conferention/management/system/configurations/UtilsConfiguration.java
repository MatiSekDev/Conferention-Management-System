package com.sii.conferention.management.system.configurations;

import java.util.Map;

public final class UtilsConfiguration {
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_EMAIL = "admin@admin.com";
    public static final String ORGANIZER_USERNAME = "organizer";
    public static final String ORGANIZER_EMAIL = "organizer@organizer.com";
    public static final String USER_EMAIL_CHANGED_SUCCESS_MESSAGE = "Email was updated!";
    public static final String USER_ADD_FAILURE_MESSAGE = "Failed to save user";
    public static final String PARTICIPANT_ADD_FAILURE_ALREADY_JOINED_MESSAGE = "Failed to join lecture, you have already joined it";
    public static final String PARTICIPANT_ADD_FAILURE_MESSAGE = "Failed to join lecture";
    public static final String PARTICIPANT_ADD_FAILURE_LIMIT_MESSAGE = "Failed to join lecture because too many people have already joined it";
    public static final String PARTICIPANT_ADD_FAILURE_CONFLICT_MESSAGE = "Failed to join lecture because of time conflict";
    public static final String USER_ADD_SUCCESS_MESSAGE = "User was added";
    public static final String USER_ALREADY_EXIST_MESSAGE = "This username is already binded to this email";

    public static final String USER_LOGIN_ALREADY_TAKEN = "Podany login jest już zajęty";

    public static final String USER_DOES_NOT_EXIST = "User does not exist";
    public static final String LECTURE_DOES_NOT_EXIST = "Lecture does not exist";

    public static final String LECTURE_JOINED_SUCCESS_MESSAGE = "You have joined the lecture";
    public static final String USER_IS_NOT_ADMIN = "Your level of permissions is not enough for that!";
    public static final String EMAIL_NOTIFICATION_MESSAGE = "You have joined the lecture!";
    public static final String EMAIL_NOTIFICATION_FILE_NAME = "./conferention.management.system/powiadomienia";
    public static final String PARTICiPANT_LECTURE_NOT_QUIT_MESSAGE = "User could not have successfully quit the lecture";
    public static final String PARTICiPANT_LECTURE_QUIT_MESSAGE = "User successfully quit the lecture";
    public static final String JSON_PARSING_EXCEPTION_MESSAGE_ENGLISH = "JSON processing error, please try again later!";
    public static final int MAX_NO_PARTICIPANT_CASE_NORMAL = 5;
    public static final int LECTURE_FIRST_START_HOUR = 10;
    public static final int LECTURE_SECOND_START_HOUR = 12;
    public static final int LECTURE_THIRD_START_HOUR = 14;
    public static final int LECTURE_START_MINUTE = 0;
    public static final int LECTURE_FIRST_END_HOUR = 11;
    public static final int LECTURE_SECOND_END_HOUR = 13;
    public static final int LECTURE_THIRD_END_HOUR = 15;
    public static final int LECTURE_END_MINUTE = 45;
}
