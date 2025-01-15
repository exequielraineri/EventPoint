/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.entity;

import com.exeraineri.eventpoint.backend.enumeration.EnumEventStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author Exequiel
 *
 */
@Slf4j
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private int capacity;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EnumEventStatus status = EnumEventStatus.ACTIVO;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;
    private String imageUrl;
    private BigDecimal basePrice;
    @Builder.Default
    private Boolean isActive = Boolean.TRUE;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Location location;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity organizer;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketType> ticketTypes = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void addTicketType(TicketType ticketType) {
        log.info("Ticket Type: {}", ticketType.toString());
        this.ticketTypes.add(ticketType);
        ticketType.setEvent(this);
    }

}
