package com.pi.event.services;

import com.pi.event.entities.Event;

import java.util.List;

public interface EventService {
    public Event save(Event event);

    public Event findEventById(Integer idEvent);

    public Event findByNameEvent(String nameEvent);

    public List<Event> findAll();

    void delete(Integer id);

    public Event updateEvent(Event event);
}
