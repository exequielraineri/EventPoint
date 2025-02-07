package com.exeraineri.eventpoint.client.data.service.implementation;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.exeraineri.eventpoint.client.R;
import com.exeraineri.eventpoint.client.data.service.IEventService;
import com.exeraineri.eventpoint.client.domain.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EventServiceImpl implements IEventService {
    List<Event> events = List.of(
            new Event(1l, "Concierto de Rock Alternativo", R.drawable.image_hero, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(5), "Teatro Central, Calle Mayor 123, La Banda"),
            new Event(2l, "Feria del Libro Independiente", R.drawable.logo_event_point, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(5), "Centro Cultural, Av. Belgrano 456, La Banda"),
            new Event(3l, "Festival de Cine Documental", R.drawable.image_hero, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(5), "Cine Municipal, Calle San Martín 234, La Banda"),
            new Event(4L, "Feria del Libro Independiente", R.drawable.logo_event_point, LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(12), "Centro Cultural, Av. Belgrano 456, La Banda"),
            new Event(5L, "Gala de Danza Contemporánea", R.drawable.image_hero, LocalDateTime.now().plusDays(15), LocalDateTime.now().plusDays(16), "Sala de Arte Moderno, Calle Buenos Aires 789, La Banda")
    );

    @Override
    public List<Event> getAllEvents() {
        return events;
    }

    @Override
    public Event getEventById(Long id) {
        return events.get(id.intValue());
    }
}
