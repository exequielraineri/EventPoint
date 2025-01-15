/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.controller;

import com.exeraineri.eventpoint.backend.dto.CategoryDto;
import com.exeraineri.eventpoint.backend.dto.EventDto;
import com.exeraineri.eventpoint.backend.entity.Category;
import com.exeraineri.eventpoint.backend.entity.Event;
import com.exeraineri.eventpoint.backend.payload.ErrorResponse;
import com.exeraineri.eventpoint.backend.payload.SuccessResponse;
import com.exeraineri.eventpoint.backend.service.interfaces.ICategoryService;
import com.exeraineri.eventpoint.backend.service.interfaces.IEventService;
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
        name = "Category Controller",
        description = "Controlador para manejar peticiones con categoria"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("categories")
public class CategoryController {

    private final ICategoryService categoryService;
    private final IEventService eventService;

    private final ModelMapper modelMapper;

    @Operation(
            summary = "Obtener lista de categorías",
            description = "Devuelve una lista de todas las categorías disponibles.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Lista de categorias",
                        content = @Content(
                                schema = @Schema(implementation = SuccessResponse.class)
                        )
                )
            }
    )
    @GetMapping
    public ResponseEntity<?> findAllCategory() {
        List<CategoryDto> categoryDtos = categoryService.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(categoryDtos)
                .message("Listado de categorias")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener una categoria",
            description = "Devuelve una categoria.",
            parameters = {
                @Parameter(
                        name = "id",
                        description = "ID de la categoría",
                        required = true,
                        schema = @Schema(type = "integer", format = "int64"),
                        in = ParameterIn.PATH
                )
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Categoria encontrada",
                        content = @Content(
                                schema = @Schema(implementation = SuccessResponse.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Categoria no encontrada",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = modelMapper.map(categoryService.findById(id), CategoryDto.class);
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(categoryDto)
                .message("Categoria encontrada")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener eventos de una categoria",
            description = "Devuelve los eventos de una categoria.",
            parameters = {
                @Parameter(
                        name = "id",
                        description = "ID de la categoría",
                        required = true,
                        schema = @Schema(type = "integer", format = "int64"),
                        in = ParameterIn.PATH
                )
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Categoria encontrada",
                        content = @Content(
                                schema = @Schema(implementation = SuccessResponse.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Categoria no encontrada",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )
            }
    )
    @GetMapping("/{id}/events")
    public ResponseEntity<?> findEventsByCategory(@PathVariable Long id) {

        List<Event> events = eventService.findByCategoryId(id);

        List<EventDto> eventsDtos = events.stream()
                .map(event -> modelMapper.map(event, EventDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(eventsDtos)
                .message("Categoria encontrada")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Guardar una categoria",
            description = "Guarda una categoria nueva.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Categoria nueva",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CategoryDto.class)
                    )
            ),
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Categoria creada",
                        content = @Content(
                                schema = @Schema(implementation = SuccessResponse.class)
                        )
                )
            }
    )
    @PostMapping
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto categoryDto) {

        Category category = categoryService.save(modelMapper.map(categoryDto, Category.class));

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(modelMapper.map(category, CategoryDto.class))
                .message("Categoria creada")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .build(),
                HttpStatus.CREATED);
    }

    @Operation(
            summary = "Modificar una categoria",
            description = "Modificar una categoria.",
            parameters = {
                @Parameter(
                        name = "id",
                        description = "ID de la categoria a modificar",
                        schema = @Schema(type = "integer", format = "int64"),
                        in = ParameterIn.PATH
                )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Categoria con campos modificados",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class)
                    )
            ),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Categoria creada",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = SuccessResponse.class)
                        )
                )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto categoryDto) {

        categoryService.update(id,
                modelMapper.map(categoryDto, Category.class));

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(categoryDto)
                .message("Categoria actualizada")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar una categoria",
            description = "Eliminar una categoria.",
            parameters = {
                @Parameter(
                        name = "id",
                        description = "ID de la categoria a eliminar",
                        schema = @Schema(type = "integer", format = "int64"),
                        in = ParameterIn.PATH
                )
            },
            responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Categoria eliminada"
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Categoria no encontrado",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
