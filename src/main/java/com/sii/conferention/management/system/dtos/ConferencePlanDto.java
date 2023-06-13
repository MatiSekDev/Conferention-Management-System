package com.sii.conferention.management.system.dtos;

import com.sii.conferention.management.system.enums.RoleEnum;
import com.sii.conferention.management.system.enums.TopicTypeEnum;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConferencePlanDto {
    private int currentParticipantsNumber;
    private int maxNumberOfParticipants;
    private Date endTime;
    private Date startTime;
    private TopicTypeEnum lectureTopicType;
}
