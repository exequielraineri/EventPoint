/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.controller;

import com.exeraineri.eventpoint.backend.dto.CategoryDto;
import com.exeraineri.eventpoint.backend.entity.Category;
import com.exeraineri.eventpoint.backend.payload.SuccessResponse;
import com.exeraineri.eventpoint.backend.service.interfaces.ICategoryService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
@RequestMapping("categories")
public class CategoryController {

    private final ICategoryService categoryService;

    private final ModelMapper modelMapper;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
