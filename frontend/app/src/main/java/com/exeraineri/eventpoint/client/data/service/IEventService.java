package com.exeraineri.eventpoint.client.data.service;

import com.exeraineri.eventpoint.client.domain.model.Event;

import java.util.List;

public interface IEventService {
    List<Event> getAllEvents();
    Event getEventById(Long id);
}
