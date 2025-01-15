/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.dto;

import com.exeraineri.eventpoint.backend.enumeration.EnumRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Exequiel
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    @NotNull(message = "Nombre es requerido")
    private String firstName;
    @NotNull(message = "Apellido es requerido")
    private String lastName;
    @Email
    @NotNull(message = "Email es requerido")
    private String email;
    private String password;
    private EnumRole role;
    private LocationDto location;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String resetPasswordToken;
    private List<CategoryDto> categories;
}
