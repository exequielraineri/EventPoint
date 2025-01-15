/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.controller;

import com.exeraineri.eventpoint.backend.dto.PurchaseTicketDto;
import com.exeraineri.eventpoint.backend.dto.TicketDto;
import com.exeraineri.eventpoint.backend.entity.Event;
import com.exeraineri.eventpoint.backend.entity.Ticket;
import com.exeraineri.eventpoint.backend.entity.TicketType;
import com.exeraineri.eventpoint.backend.entity.UserEntity;
import com.exeraineri.eventpoint.backend.exception.CustomException.ResourceNotFoundException;
import com.exeraineri.eventpoint.backend.payload.ErrorResponse;
import com.exeraineri.eventpoint.backend.payload.SuccessResponse;
import com.exeraineri.eventpoint.backend.service.interfaces.IEventService;
import com.exeraineri.eventpoint.backend.service.interfaces.ITicketService;
import com.exeraineri.eventpoint.backend.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@Tag(
        name = "Ticket Controller",
        description = "Controlador para el manejo de entradas."
)
@RestController
@RequiredArgsConstructor
@RequestMapping("tickets")
public class TicketController {

    private final ITicketService ticketService;
    private final IEventService eventService;
    private final IUserService userService;

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

    @Operation(
            summary = "Compra de ticket",
            description = "Compra de entrada seleccionando un tipo de entrada.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Compra de ticket",
                    required = true,
                    content = {
                        @Content(
                                schema = @Schema(implementation = PurchaseTicketDto.class)
                        )
                    }
            ),
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Ticket creado",
                        content = {
                            @Content(
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                        }
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "(Evento | Usuario | Tipo de Entrada) no encontrado",
                        content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                        }
                )

            }
    )
    @PostMapping
    public ResponseEntity<?> purchaseTicket(@Valid @RequestBody PurchaseTicketDto purchaseTicket) {

        Event event = eventService.findById(purchaseTicket.getEventId());
        UserEntity user = userService.findById(purchaseTicket.getUserId());
        TicketType ticketType = event.getTicketTypes().stream()
                .filter(type -> type.getId().equals(purchaseTicket.getTicketTypeId()))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("El tipo de ticket no se encontro en el evento"));

        if (purchaseTicket.getQuantity() > ticketType.getStock()) {
            throw new ResourceNotFoundException("La cantidad supera el stock, quedan: " + ticketType.getStock());
        }

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < purchaseTicket.getQuantity(); i++) {
            tickets.add(Ticket.builder()
                    .event(event)
                    .ticketType(ticketType)
                    .user(user)
                    .build());
        }

        ticketType.setStock(ticketType.getStock() - purchaseTicket.getQuantity());
        tickets = ticketService.saveAll(tickets);
        List<TicketDto> ticketsDtos = tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(ticketsDtos)
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
