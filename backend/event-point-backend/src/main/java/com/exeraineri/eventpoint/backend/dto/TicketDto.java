/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.dto;

import com.exeraineri.eventpoint.backend.enumeration.EnumTicketStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Long ticketTypeId;
    private String ticketTypeName;
    private EnumTicketStatus status;
    private LocalDateTime purchaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long paymentId;

}
