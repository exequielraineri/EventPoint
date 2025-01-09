/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.controller;

import com.exeraineri.eventpoint.backend.dto.NotificationDto;
import com.exeraineri.eventpoint.backend.dto.PaymentDto;
import com.exeraineri.eventpoint.backend.entity.Notification;
import com.exeraineri.eventpoint.backend.entity.Payment;
import com.exeraineri.eventpoint.backend.payload.SuccessResponse;
import com.exeraineri.eventpoint.backend.service.interfaces.IPaymentService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Exequiel
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("payments")
public class PaymentController {

    private final IPaymentService paymentService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> findAllPayment() {
        List<PaymentDto> paymentDtos = paymentService.findAll().stream()
                .map(payment -> modelMapper.map(payment, PaymentDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(paymentDtos)
                .message("Listado de pagos")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPaymentById(@PathVariable Long id) {
        PaymentDto paymentDto = modelMapper.map(paymentService.findById(id), PaymentDto.class);
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(paymentDto)
                .message("Pago encontrado")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> savePayment(@Valid @RequestBody PaymentDto paymentDto) {

        Payment payment = paymentService.save(modelMapper.map(paymentDto, Payment.class));

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(modelMapper.map(payment, NotificationDto.class))
                .message("Pago creado")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentDto paymentDto) {

        paymentService.update(id,
                modelMapper.map(paymentDto, Payment.class));

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(paymentDto)
                .message("Pago actualizado")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        paymentService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
