/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.service.implementation;

import com.exeraineri.eventpoint.backend.entity.Payment;
import com.exeraineri.eventpoint.backend.entity.Ticket;
import com.exeraineri.eventpoint.backend.enumeration.EnumPaymentStatus;
import com.exeraineri.eventpoint.backend.enumeration.EnumTicketStatus;
import com.exeraineri.eventpoint.backend.exception.CustomException.ResourceNotFoundException;
import com.exeraineri.eventpoint.backend.repository.IPaymentRepository;
import com.exeraineri.eventpoint.backend.service.interfaces.IPaymentService;
import com.exeraineri.eventpoint.backend.service.interfaces.ITicketService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {
    
    private final IPaymentRepository paymentRepository;
    
    private final ITicketService ticketService;
    
    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
    
    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago con ID " + id + " no encontrado"));
    }
    
    @Override
    public Payment save(Payment payment) {
        Ticket ticket = ticketService.findById(payment.getTicket().getId());
        ticket.setPurchaseDate(LocalDateTime.now());
        payment.setStatus(EnumPaymentStatus.APROBADO);
        payment.setAmount(ticket.getTicketType().getPrice());
        ticket.setPayment(payment);
        ticket.setStatus(EnumTicketStatus.COMPRADA);
        payment.setTicket(ticket);
        return paymentRepository.save(payment);
    }
    
    @Override
    public Payment update(Long id, Payment payment) {
        Payment paymentBD = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago con ID " + id + " no encontrado"));
        paymentBD.setAmount(payment.getAmount());
        paymentBD.setMethod(payment.getMethod());
        paymentBD.setUpdatedAt(LocalDateTime.now());
        return paymentRepository.save(payment);
    }
    
    @Override
    public void deleteById(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pago con ID " + id + " no encontrado");
        }
        paymentRepository.deleteById(id);
    }
    
}
