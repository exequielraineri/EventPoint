/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.controller;

import com.exeraineri.eventpoint.backend.dto.UserDto;
import com.exeraineri.eventpoint.backend.entity.UserEntity;
import com.exeraineri.eventpoint.backend.payload.SuccessResponse;
import com.exeraineri.eventpoint.backend.service.interfaces.IUserService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Exequiel
 */
@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> findAllUser() {
        List<UserDto> userList = userService.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(userList)
                .message("Lista de usuarios")
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        UserEntity user = userService.findById(id);

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(modelMapper.map(user, UserDto.class))
                        .message("Usuario encontrado")
                        .status(HttpStatus.OK.value())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto userDto) {
        log.info("Usuario {}", userDto.toString());
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity = userService.save(userEntity);

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(modelMapper.map(userEntity, UserDto.class))
                        .message("Usuario creado")
                        .status(HttpStatus.CREATED.value())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity = userService.update(id, userEntity);
        log.info("Usuario {}", userDto.toString());
        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(modelMapper.map(userEntity, UserDto.class))
                        .message("Usuario actualizado")
                        .status(HttpStatus.CREATED.value())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
