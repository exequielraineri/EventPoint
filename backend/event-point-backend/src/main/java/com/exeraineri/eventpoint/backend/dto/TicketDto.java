/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.dto;

import com.exeraineri.eventpoint.backend.entity.Payment;
import com.exeraineri.eventpoint.backend.entity.TicketType;
import com.exeraineri.eventpoint.backend.enumeration.EnumTicketStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
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
public class TicketDto {

    private Long id;
    private Long userId;
    private Long eventId;
    @JsonIgnoreProperties(value = "event")
    private TicketTypeDto ticketType;
    private EnumTicketStatus status;
    private LocalDateTime purchaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonIgnoreProperties(value = "ticket")
    private PaymentDto payment;

}
