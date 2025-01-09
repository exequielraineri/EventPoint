/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.controller;

import com.exeraineri.eventpoint.backend.dto.CategoryDto;
import com.exeraineri.eventpoint.backend.dto.EventDto;
import com.exeraineri.eventpoint.backend.entity.Category;
import com.exeraineri.eventpoint.backend.entity.Event;
import com.exeraineri.eventpoint.backend.payload.SuccessResponse;
import com.exeraineri.eventpoint.backend.service.interfaces.ICategoryService;
import com.exeraineri.eventpoint.backend.service.interfaces.IEventService;
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
@RequestMapping("events")
public class EventController {

    private final IEventService eventService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> findAllEvent() {
        List<EventDto> eventDtos = eventService.findAll().stream()
                .map(event -> modelMapper.map(event, EventDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(eventDtos)
                .message("Listado de eventos")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findEventById(@PathVariable Long id) {
        EventDto eventDto = modelMapper.map(eventService.findById(id), EventDto.class);
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(eventDto)
                .message("Evento encontrada")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveEvent(@Valid @RequestBody EventDto eventDto) {

        Event event = eventService.save(modelMapper.map(eventDto, Event.class));

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(modelMapper.map(event, EventDto.class))
                .message("Evento creado")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventDto eventDto) {

        eventService.update(id,
                modelMapper.map(eventDto, Event.class));

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(eventDto)
                .message("Evento actualizado")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
