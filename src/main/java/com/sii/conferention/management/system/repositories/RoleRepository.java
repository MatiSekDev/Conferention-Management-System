package com.sii.conferention.management.system.repositories;

import com.sii.conferention.management.system.entities.RoleEntity;
import com.sii.conferention.management.system.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("SELECT role FROM RoleEntity role WHERE role.name = :name")
    Optional<RoleEntity> findRoleByName(@Param("name") RoleEnum name);
}
