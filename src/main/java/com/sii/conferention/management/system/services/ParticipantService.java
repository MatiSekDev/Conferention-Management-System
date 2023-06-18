package com.sii.conferention.management.system.services;

import com.sii.conferention.management.system.entities.LectureEntity;
import com.sii.conferention.management.system.entities.ParticipantEntity;
import com.sii.conferention.management.system.entities.UserEntity;
import com.sii.conferention.management.system.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public ParticipantEntity assignUserToLecture(UserEntity user, LectureEntity lecture) {
        return participantRepository.save(
                ParticipantEntity.builder().user(user).lecture(lecture).build()
        );
    }

    public boolean removeUserFromLecture(UserEntity user, LectureEntity lecture) {
        Optional<ParticipantEntity> existingParticipant = participantRepository.findByLectureAndUser(lecture, user);
        if (existingParticipant.isEmpty()) {
            return false;
        }
        participantRepository.delete(existingParticipant.get());
        return true;
    }
}
