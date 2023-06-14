package com.sii.conferention.management.system.services;

import com.sii.conferention.management.system.dtos.NewUserDto;
import com.sii.conferention.management.system.entities.RoleEntity;
import com.sii.conferention.management.system.entities.UserEntity;
import com.sii.conferention.management.system.enums.RoleEnum;
import com.sii.conferention.management.system.repositories.RoleRepository;
import com.sii.conferention.management.system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public boolean isUserLoginTaken(NewUserDto newUserDto) {
        return userRepository.findByUsername(newUserDto.getUsername())
                             .filter(user -> !user.getEmail().equals(newUserDto.getEmail()))
                             .isPresent();
    }

    public boolean doesUserAlreadyExist(NewUserDto newUserDto) {
        return userRepository.findByUsernameAndEmail(newUserDto.getUsername(), newUserDto.getEmail())
                             .isPresent();
    }
    public UserEntity saveUser(UserEntity user, RoleEnum role) {
        Optional<RoleEntity> existingRole = roleRepository.findRoleByName(role);
        existingRole.ifPresent(
                roleEntity -> {
                    user.getRoles().add(roleEntity);
                }
        );

        return userRepository.save(user);
    }
}
