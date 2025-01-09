/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.controller;

import com.exeraineri.eventpoint.backend.dto.NotificationDto;
import com.exeraineri.eventpoint.backend.dto.PaymentDto;
import com.exeraineri.eventpoint.backend.dto.TicketDto;
import com.exeraineri.eventpoint.backend.entity.Payment;
import com.exeraineri.eventpoint.backend.entity.Ticket;
import com.exeraineri.eventpoint.backend.payload.SuccessResponse;
import com.exeraineri.eventpoint.backend.service.interfaces.IPaymentService;
import com.exeraineri.eventpoint.backend.service.interfaces.ITicketService;
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
@RequestMapping("tickets")
public class TicketController {

    private final ITicketService ticketService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> findAllTicket() {
        List<TicketDto> ticketDtos = ticketService.findAll().stream()
                .map(ticket -> modelMapper.map(ticket, TicketDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(ticketDtos)
                .message("Listado de entradas")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTicketById(@PathVariable Long id) {
        TicketDto ticketDto = modelMapper.map(ticketService.findById(id), TicketDto.class);
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(ticketDto)
                .message("Entrada encontrada")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveTicket(@Valid @RequestBody TicketDto ticketDto) {

        Ticket ticket = ticketService.save(modelMapper.map(ticketDto, Ticket.class));

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(modelMapper.map(ticket, TicketDto.class))
                .message("Entrada creada")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketDto ticketDto) {

        ticketService.update(id,
                modelMapper.map(ticketDto, Ticket.class));

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(ticketDto)
                .message("Entrada actualizada")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        ticketService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
