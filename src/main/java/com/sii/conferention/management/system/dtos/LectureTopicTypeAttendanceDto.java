package com.sii.conferention.management.system.dtos;

import com.sii.conferention.management.system.enums.TopicTypeEnum;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureTopicTypeAttendanceDto {
    private TopicTypeEnum lectureTopicType;
    private Long sumOfAttendee;
}
