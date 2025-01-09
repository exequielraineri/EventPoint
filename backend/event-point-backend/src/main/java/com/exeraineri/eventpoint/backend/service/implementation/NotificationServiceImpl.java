/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.service.implementation;

import com.exeraineri.eventpoint.backend.entity.Notification;
import com.exeraineri.eventpoint.backend.exception.CustomException.ResourceNotFoundException;
import com.exeraineri.eventpoint.backend.repository.INotificationRepository;
import com.exeraineri.eventpoint.backend.service.interfaces.INotificationService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    private final INotificationRepository notificationRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificacion con ID " + id + " no se encontro"));
    }

    @Override
    public Notification save(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    @Override
    public Notification update(Long id, Notification notification) {
        Notification notificationDB = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificacion con ID " + id + " no se encontro"));

        notificationDB.setMessage(notification.getMessage());
        notificationDB.setEvent(notification.getEvent());
        notificationDB.setUser(notification.getUser());
        return notificationRepository.save(notificationDB);
    }

    @Override
    public void deleteById(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notificacion con ID " + id + " no se encontro");
        }
        notificationRepository.deleteById(id);
    }

}
