package com.sii.conferention.management.system.services;

import com.sii.conferention.management.system.configurations.UtilsConfiguration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

@Service
public class LoggingService {
    public void writeToEmailFileLog(String message) {
        try {
            Files.writeString(
                    Path.of(UtilsConfiguration.EMAIL_NOTIFICATION_FILE_NAME),
                    message + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public String parseToMessage(String email, String message) {
        return " " + LocalDate.now() + " " + email + " " + message;
    }
}
