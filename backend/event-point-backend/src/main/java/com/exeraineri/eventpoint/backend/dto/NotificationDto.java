/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Exequiel
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private Long id;
    @NotNull(message = "Mensaje requerido")
    private String message;
    private Long userId;
    private Long eventId;
    private LocalDateTime createdAt;
}
