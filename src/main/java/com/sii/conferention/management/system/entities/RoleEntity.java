package com.sii.conferention.management.system.entities;

import com.sii.conferention.management.system.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private RoleEnum name;

    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    Set<UserEntity> users = new HashSet<>();
}
