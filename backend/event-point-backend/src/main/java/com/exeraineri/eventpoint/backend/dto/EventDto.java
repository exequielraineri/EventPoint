/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.dto;

import com.exeraineri.eventpoint.backend.enumeration.EnumEventStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private Long id;
    @NotNull(message = "Titulo es requerido")
    private String title;
    private String description;
    private int capacity;
    private EnumEventStatus status;
    @NotNull(message = "Fecha inicio es requerido")
    private LocalDateTime startDate;
    @NotNull(message = "Fecha fin es requerido")
    private LocalDateTime endDate;
    private String imageUrl;
    private BigDecimal basePrice;
    private CategoryDto category;
    private LocationDto location;
    private Long organizerId;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TicketTypeDto> ticketTypes;

}
