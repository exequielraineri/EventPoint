/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.controller;

import com.exeraineri.eventpoint.backend.dto.EventDto;
import com.exeraineri.eventpoint.backend.dto.NotificationDto;
import com.exeraineri.eventpoint.backend.entity.Event;
import com.exeraineri.eventpoint.backend.entity.Notification;
import com.exeraineri.eventpoint.backend.payload.SuccessResponse;
import com.exeraineri.eventpoint.backend.service.interfaces.IEventService;
import com.exeraineri.eventpoint.backend.service.interfaces.INotificationService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("notifications")
public class NotificationController {

    private final INotificationService notificationService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> findAllNotification() {
        List<NotificationDto> notificationDtos = notificationService.findAll().stream()
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(notificationDtos)
                .message("Listado de notificaciones")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findNotificationById(@PathVariable Long id) {
        NotificationDto notificationDto = modelMapper.map(notificationService.findById(id), NotificationDto.class);
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(notificationDto)
                .message("Notificacion encontrada")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveNotification(@Valid @RequestBody NotificationDto notificationDto) {

        Notification notification = notificationService.save(modelMapper.map(notificationDto, Notification.class));

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(modelMapper.map(notification, NotificationDto.class))
                .message("Notificacion creada")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotification(
            @PathVariable Long id,
            @Valid @RequestBody NotificationDto notificationDto) {

        notificationService.update(id,
                modelMapper.map(notificationDto, Notification.class));

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(notificationDto)
                .message("Notificacion actualizada")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificationService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
