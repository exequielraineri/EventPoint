/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
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
public class TicketTypeDto {

    private Long id;
    @NotNull(message = "Evento es requerido")
    private EventDto event;
    @NotNull(message = "Nombre es requerido")
    private String name;
    @NotNull(message = "Stock es requerido")
    private int stock;
    @NotNull(message = "Precio es requerido")
    private BigDecimal price;
}
