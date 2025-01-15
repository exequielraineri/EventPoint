/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class PurchaseTicketDto {

    @NotNull(message = "EventId es requerido")
    private Long eventId;
    @NotNull(message = "UserId es requerido")
    private Long userId;
    @NotNull(message = "TicketTypeId es requerido")
    private Long ticketTypeId;
    @Min(value = 1)
    private int quantity;
}
