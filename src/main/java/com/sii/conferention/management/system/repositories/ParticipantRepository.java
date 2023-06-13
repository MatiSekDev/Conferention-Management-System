package com.sii.conferention.management.system.repositories;

import com.sii.conferention.management.system.entities.LectureEntity;
import com.sii.conferention.management.system.entities.ParticipantEntity;
import com.sii.conferention.management.system.entities.RoleEntity;
import com.sii.conferention.management.system.enums.RoleEnum;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<LectureEntity, Long> {
    @Query("SELECT count(participant.id) FROM ParticipantEntity participant WHERE lecture.id = :id")
    int countParticipantsByLecture(@Param("id") long id);
}