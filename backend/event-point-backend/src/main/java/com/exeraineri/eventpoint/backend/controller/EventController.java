/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.controller;

import com.exeraineri.eventpoint.backend.dto.EventDto;
import com.exeraineri.eventpoint.backend.dto.TicketDto;
import com.exeraineri.eventpoint.backend.entity.Event;
import com.exeraineri.eventpoint.backend.entity.Ticket;
import com.exeraineri.eventpoint.backend.payload.ErrorResponse;
import com.exeraineri.eventpoint.backend.payload.SuccessResponse;
import com.exeraineri.eventpoint.backend.service.interfaces.IEventService;
import com.exeraineri.eventpoint.backend.service.interfaces.ITicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Event Controller",
        description = "Controlador para manejar peticiones con eventos"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("events")
public class EventController {

    private final IEventService eventService;
    private final ITicketService ticketService;

    private final ModelMapper modelMapper;

    @Operation(
            summary = "Listar eventos",
            description = "Obtener listado de eventos disponibles.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Lista de eventos",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = SuccessResponse.class)
                        )
                )
            }
    )
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

    @Operation(
            summary = "Obtener un evento",
            description = "Obtener un evento disponible.",
            parameters = {
                @Parameter(
                        name = "id",
                        description = "ID del evento",
                        in = ParameterIn.PATH,
                        schema = @Schema(type = "integer", format = "int64"),
                        required = true
                )
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Evento encontrado",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = SuccessResponse.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Evento no encontrado",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )
            }
    )
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

    @Operation(
            summary = "Tickets de un evento",
            description = "Devuelve los tickets de un evento determinado",
            parameters = {
                @Parameter(
                        name = "id",
                        description = "ID del evento",
                        in = ParameterIn.PATH,
                        required = true,
                        schema = @Schema(type = "integer", format = "int64")
                )
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Tickets encontrados",
                        content = {
                            @Content(
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                        }
                )
            }
    )
    @GetMapping("/{id}/tickets")
    public ResponseEntity<?> findTicketByEventoId(@PathVariable Long id) {
        List<Ticket> tickets = ticketService.findByEventId(id);

        List<TicketDto> ticketsDtos = tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(ticketsDtos)
                .message("Tickets del evento: " + id)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Guardar un evento",
            description = "Guardar un evento nuevo.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Evento a guardar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EventDto.class)
                    )
            ),
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Evento creado",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = SuccessResponse.class)
                        )
                )

            }
    )
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

    @Operation(
            summary = "Modificar un evento",
            description = "Modificar un evento disponible.",
            parameters = {
                @Parameter(
                        name = "id",
                        description = "ID del evento",
                        in = ParameterIn.PATH,
                        schema = @Schema(type = "integer", format = "int64"),
                        required = true
                )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Evento con campos a modificar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EventDto.class)
                    )
            ),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Evento modificado",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = SuccessResponse.class)
                        )
                )

            }
    )
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

    @Operation(
            summary = "Eliminar un evento",
            description = "Eliminar un evento.",
            parameters = {
                @Parameter(
                        name = "id",
                        description = "ID del evento",
                        required = true,
                        schema = @Schema(type = "integer", format = "int64"),
                        in = ParameterIn.PATH
                )
            },
            responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Evento eliminado"
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Evento no encontrado",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
