package com.sii.conferention.management.system.entities;

import com.sii.conferention.management.system.enums.TopicTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lectures")
public class LectureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date startTime;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date endTime;

    @Column
    private int maxNumberOfParticipants;

    @Column(nullable = false)
    private TopicTypeEnum topicType;

    @OneToMany(mappedBy = "lecture")
    private Set<ParticipantEntity> participants;
}
