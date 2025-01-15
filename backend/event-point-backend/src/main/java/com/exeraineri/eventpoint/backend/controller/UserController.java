/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.controller;

import com.exeraineri.eventpoint.backend.dto.CategoryDto;
import com.exeraineri.eventpoint.backend.dto.EventDto;
import com.exeraineri.eventpoint.backend.dto.UserDto;
import com.exeraineri.eventpoint.backend.entity.Category;
import com.exeraineri.eventpoint.backend.entity.Event;
import com.exeraineri.eventpoint.backend.entity.UserEntity;
import com.exeraineri.eventpoint.backend.payload.ErrorResponse;
import com.exeraineri.eventpoint.backend.payload.SuccessResponse;
import com.exeraineri.eventpoint.backend.service.interfaces.ICategoryService;
import com.exeraineri.eventpoint.backend.service.interfaces.IEventService;
import com.exeraineri.eventpoint.backend.service.interfaces.IUserService;
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
@Tag(
        name = "User Controller",
        description = "Controlador para manejar la logica de usuarios."
)
@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IEventService eventService;
    private final ICategoryService categoryService;

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

    @Operation(
            summary = "Eventos de un organizador",
            description = "Devuelve una lista de eventos de un organizador",
            parameters = @Parameter(
                    name = "id",
                    description = "ID del organizador",
                    in = ParameterIn.PATH,
                    required = true,
                    schema = @Schema(type = "integer", format = "int64")
            ),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Eventos de un organizador",
                        content = {
                            @Content(
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                        }
                )
            }
    )
    @GetMapping("/{id}/events")
    public ResponseEntity<?> findEventsByUserId(@PathVariable Long id) {

        List<Event> events = eventService.findByOrganizerId(id);

        List<EventDto> eventsDtos = events.stream()
                .map(event -> modelMapper.map(event, EventDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(eventsDtos)
                        .message("Eventos del organizador : " + id)
                        .status(HttpStatus.OK.value())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Guardar un usuario",
            description = "Guardar un usuario nuevo.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Usuario nuevo",
                    required = true,
                    content = {
                        @Content(
                                schema = @Schema(implementation = UserDto.class)
                        )
                    }
            ),
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Usuario creado",
                        content = @Content(
                                schema = @Schema(implementation = SuccessResponse.class)
                        )
                )
            }
    )
    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto userDto) {
        log.info("Usuario {}", userDto.toString());
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
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

    @Operation(
            summary = "Modificar un usuario",
            description = "Modificar un usuario.",
            parameters = @Parameter(
                    name = "id",
                    description = "ID del usuario",
                    in = ParameterIn.PATH,
                    schema = @Schema(type = "integer", format = "int64"),
                    required = true
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Usuario con campos modificado",
                    required = true,
                    content = {
                        @Content(
                                schema = @Schema(implementation = UserDto.class)
                        )
                    }
            ),
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Usuario modificado",
                        content = @Content(
                                schema = @Schema(implementation = SuccessResponse.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Usuario no encontrado",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )
            }
    )
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

    @Operation(
            summary = "Categorias de un usuario",
            description = "Devuelve una lista de categorias de un usuario",
            parameters = @Parameter(
                    name = "id",
                    description = "ID del usuario",
                    in = ParameterIn.PATH,
                    required = true,
                    schema = @Schema(type = "integer", format = "int64")
            ),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Categorias de un usuario",
                        content = {
                            @Content(
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                        }
                )
            }
    )
    @GetMapping("/{id}/categories")
    public ResponseEntity<?> findCategoriesByUserId(@PathVariable Long id) {
        List<Category> categories = userService.findById(id).getCategories();

        List<CategoryDto> categoriesDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(categoriesDtos)
                        .message("Categorias del usuario : " + id)
                        .status(HttpStatus.OK.value())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.OK);
    }
}
