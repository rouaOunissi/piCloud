package com.pi.event.services;

import com.pi.event.entities.Event;
import com.pi.event.exceptions.EntityNotFoundException;
import com.pi.event.exceptions.ErrorCodes;
import com.pi.event.exceptions.InvalidEntityException;
import com.pi.event.repositories.EventRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class EventServiceImp implements EventService {


    public final EventRepository eventRepository;

    @Override
    public Event save(Event event) {
        if (event == null) {
            log.error("Event is not valid {}", event);
            throw new InvalidEntityException("L'Event n'est pas valide", ErrorCodes.EVENT_NOT_VALID);
        }
        
        return this.eventRepository.save(event);
    }

    @Override
    public Event findEventById(Integer id) {
        if (id == null) {
            log.error("Event ID is null");
            return null;
        }
        return this.eventRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun EVENT avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.EVENT_NOT_FOUND)
        );
    }

    @Override
    public Event findByNameEvent(String nameEvent) {
        if (!StringUtils.hasLength(nameEvent)) {
            log.error("Event NAME is null");
            return null;
        }
        return eventRepository.findEventByEventName(nameEvent)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucun event avec le nom = " + nameEvent + " n' ete trouve dans la BDD",
                                ErrorCodes.EVENT_NOT_FOUND)
                );
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Event ID is null");
            return;
        }
        // check if there is associaion
        this.eventRepository.deleteById(id);
    }

    @Override
    public Event updateEvent(Event event) {
        if (event == null) {
            log.error("Event is not valid {}", event);
            throw new InvalidEntityException("L'Event n'est pas valide", ErrorCodes.EVENT_NOT_VALID);
        }
        return this.eventRepository.save(event);
    }
}
