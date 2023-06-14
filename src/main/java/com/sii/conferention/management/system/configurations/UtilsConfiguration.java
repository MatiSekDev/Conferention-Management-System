package com.sii.conferention.management.system.configurations;

import java.util.Map;

public final class UtilsConfiguration {
    public static final String SITE_URL = "http://127.0.0.1:8080";
    public static final String API_VERSION = "/v0";
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_EMAIL = "admin@admin.com";
    public static final String ORGANIZER_USERNAME = "organizer";
    public static final String ORGANIZER_EMAIL = "organizer@organizer.com";

    public static final String USER_ADD_FAILURE_MESSAGE = "Failed to save user";

    public static final String USER_ADD_SUCCESS_MESSAGE = "User was added";
    public static final String USER_ALREADY_EXIST_MESSAGE = "This username is already binded to this email";

    public static final String USER_LOGIN_ALREADY_TAKEN = "Podany login jest już zajęty";

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
