package com.sii.conferention.management.system.repositories;

import com.sii.conferention.management.system.dtos.LectureTopicTypeAttendanceDto;
import com.sii.conferention.management.system.entities.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Long> {
    Optional<LectureEntity> findLectureById(@Param("id") Long id);

    @Query("SELECT lecture.topicType, COUNT(participant) FROM LectureEntity lecture LEFT JOIN lecture.participants participant GROUP BY lecture.topicType")
    List<Object[]> countParticipantsByLectureTopicType();

    @Query("SELECT lecture FROM LectureEntity lecture WHERE lecture.id in :lecturesIdsUserIsAssignedTo")
    List<LectureEntity> findLecturesByLecturesId(List<Long> lecturesIdsUserIsAssignedTo);
}
