package com.sii.conferention.management.system.dtos;

import com.sii.conferention.management.system.entities.LectureEntity;
import com.sii.conferention.management.system.enums.TopicTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLecturesDto {
    private Long id;
    private Date startTime;
    private Date endTime;
    private TopicTypeEnum topicType;

    public static List<UserLecturesDto> getListOfUserLecturesDtosFromListOdLectureEntities(List<LectureEntity> lectureEntities) {
        List<UserLecturesDto> parsedList = new LinkedList<UserLecturesDto>();
        for (LectureEntity lecture : lectureEntities) {
            parsedList.add(
                    UserLecturesDto.builder()
                                   .endTime(lecture.getEndTime())
                                   .startTime(lecture.getStartTime())
                                   .topicType(lecture.getTopicType())
                                   .id(lecture.getId())
                                   .build()
            );
        }
        return parsedList;
    }
}
