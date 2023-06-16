package com.sii.conferention.management.system.repositories;

import com.sii.conferention.management.system.entities.LectureEntity;
import com.sii.conferention.management.system.entities.ParticipantEntity;
import com.sii.conferention.management.system.entities.RoleEntity;
import com.sii.conferention.management.system.entities.UserEntity;
import com.sii.conferention.management.system.enums.RoleEnum;
import jakarta.persistence.Id;
import org.hibernate.mapping.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {
    @Query("SELECT count(participant.id) FROM ParticipantEntity participant WHERE participant.lecture.id = :id")
    int countParticipantsByLecture(@Param("id") long id);

    @Query("SELECT participant.lecture.id FROM ParticipantEntity participant WHERE participant.user.id = :userId")
    List<Long> findLecturesIdsUserIsAssignedTo(@Param("userId") long userId);

    Optional<ParticipantEntity> findByLectureAndUser(@Param("lecture") LectureEntity lecture, @Param("user") UserEntity user);
}