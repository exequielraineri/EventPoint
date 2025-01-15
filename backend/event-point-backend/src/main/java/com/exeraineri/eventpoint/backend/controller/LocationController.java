/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.controller;

import com.exeraineri.eventpoint.backend.dto.LocationDto;
import com.exeraineri.eventpoint.backend.entity.Location;
import com.exeraineri.eventpoint.backend.payload.SuccessResponse;
import com.exeraineri.eventpoint.backend.service.interfaces.ILocationService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("locations")
@RequiredArgsConstructor
public class LocationController {

    private final ILocationService locationService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> findAllLocation() {
        List<LocationDto> locationList = locationService.findAll().stream()
                .map(location -> modelMapper.map(location, LocationDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(locationList)
                        .message("Listado de ubicaciones")
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findLocationById(@PathVariable Long id) {
        Location location = locationService.findById(id);

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(modelMapper.map(location, LocationDto.class))
                        .message("Ubicacion encontrada")
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> saveLocation(@Valid @RequestBody LocationDto location) {
        Location locationSave = locationService.save(
                modelMapper.map(location, Location.class));

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(modelMapper.map(locationSave, LocationDto.class))
                        .message("Ubicacion guardada")
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CREATED.value())
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable Long id, @Valid @RequestBody LocationDto location) {
        Location locationSave = locationService.update(id,
                modelMapper.map(location, Location.class));

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(modelMapper.map(locationSave, LocationDto.class))
                        .message("Ubicacion actualizada")
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CREATED.value())
                        .build(),
                HttpStatus.CREATED
        );
    }
}
