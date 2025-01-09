/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.service.implementation;

import com.exeraineri.eventpoint.backend.entity.Ticket;
import com.exeraineri.eventpoint.backend.exception.CustomException.ResourceNotFoundException;
import com.exeraineri.eventpoint.backend.repository.ITicketRepository;
import com.exeraineri.eventpoint.backend.service.interfaces.ITicketService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements ITicketService {

    private final ITicketRepository ticketRepository;

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket con ID " + id + " no encontrado"));
    }

    @Override
    public Ticket save(Ticket ticket) {
        if (ticket.getTicketType().getStock() == 0) {
            throw new ResourceNotFoundException("Ya no quedan entradas de tipo " + ticket.getTicketType().getName());
        }
        ticket.getTicketType().setStock(ticket.getTicketType().getStock() - 1);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket update(Long id, Ticket ticket) {
        Ticket ticketBD = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket con ID " + id + " no encontrado"));
        ticketBD.setPurchaseDate(ticket.getPurchaseDate());
        ticketBD.setStatus(ticket.getStatus());
        ticketBD.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticketBD);
    }

    @Override
    public void deleteById(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket con ID " + id + " no encontrado");
        }
        ticketRepository.deleteById(id);
    }

}
