/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.service.implementation;

import com.exeraineri.eventpoint.backend.entity.UserEntity;
import com.exeraineri.eventpoint.backend.exception.CustomException;
import com.exeraineri.eventpoint.backend.exception.CustomException.EmailAlreadyExistsException;
import com.exeraineri.eventpoint.backend.exception.CustomException.ResourceNotFoundException;
import com.exeraineri.eventpoint.backend.repository.IUserRepository;
import com.exeraineri.eventpoint.backend.service.interfaces.IUserService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no existe"));
    }

    @Override
    public UserEntity save(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("El email " + user.getEmail() + " ya está en uso");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario con ID " + id + " no existe");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity update(Long id, UserEntity user) {
        UserEntity userBD = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no existe"));

        userBD.setFirstName(user.getFirstName());
        userBD.setLastName(user.getLastName());
        userBD.setEmail(user.getEmail());
        userBD.setIsActive(user.getIsActive());
        userBD.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(userBD);
    }

}
