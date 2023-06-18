package com.sii.conferention.management.system.dtos;

import com.sii.conferention.management.system.enums.TopicTypeEnum;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LecturePercentageAttendanceDto {
    private long conferenceIdentifier;
    private int percentageAttendance;
    private Date endTime;
    private Date startTime;
    private TopicTypeEnum lectureTopicType;
}
