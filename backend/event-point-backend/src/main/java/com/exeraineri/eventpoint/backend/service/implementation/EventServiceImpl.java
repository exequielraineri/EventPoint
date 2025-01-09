/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.service.implementation;

import com.exeraineri.eventpoint.backend.entity.Category;
import com.exeraineri.eventpoint.backend.entity.Event;
import com.exeraineri.eventpoint.backend.entity.UserEntity;
import com.exeraineri.eventpoint.backend.exception.CustomException.ResourceNotFoundException;
import com.exeraineri.eventpoint.backend.repository.IEventRepository;
import com.exeraineri.eventpoint.backend.service.interfaces.ICategoryService;
import com.exeraineri.eventpoint.backend.service.interfaces.IEventService;
import com.exeraineri.eventpoint.backend.service.interfaces.IUserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService {

    private final IEventRepository eventRepository;

    private final IUserService userService;

    private final ICategoryService categoryService;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento con ID " + id + " no encontrado"));
    }

    @Override
    public Event save(Event event) {
        UserEntity user = userService.findById(event.getOrganizer().getId());
        Category category = categoryService.findById(event.getCategory().getId());

        event.setOrganizer(user);
        event.setCategory(category);
        event.setCreatedAt(LocalDateTime.now());
        event.setIsActive(Boolean.TRUE);
        return eventRepository.save(event);
    }

    @Override
    public Event update(Long id, Event event) {
        Event eventBD = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento con ID " + id + " no encontrado"));

        Category category = categoryService.findById(event.getCategory().getId());

        eventBD.setBasePrice(event.getBasePrice());
        eventBD.setCapacity(event.getCapacity());
        eventBD.setDescription(event.getDescription());
        eventBD.setTitle(event.getTitle());
        eventBD.setStatus(event.getStatus());
        eventBD.setCategory(category);
        return eventRepository.save(eventBD);
    }

    @Override
    public void deleteById(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evento con ID " + id + " no encontrado");
        }
        eventRepository.deleteById(id);
    }

}
