package com.pi.event.services;

import com.pi.event.entities.Event;

import java.io.IOException;
import java.util.List;

public interface EventService {
    public Event save(Event event);

    public Event findEventById(Integer idEvent);

    public Event findByNameEvent(String nameEvent);

    public List<Event> findAll();

    void delete(Integer id);

    boolean updateEvent(Integer id , Event event) ;
}
