package com.pi.event.services;
import com.pi.event.entities.Event;
import com.pi.event.repositories.EventRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;


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
        }
        return this.eventRepository.save(event);
    }

    @Override
    public Event findEventById(Integer id)  {
        if (id == null) {
            log.error("Event ID is null");
            return null;
        }
        return this.eventRepository.findById(id).orElse(null);
    }


    @Override
    public Event findByNameEvent(String nameEvent) {
        if (!StringUtils.hasLength(nameEvent)) {
            log.error("Event NAME is null");
            return null;
        }
        return this.eventRepository.findEventByEventName(nameEvent).orElse(null);
    }

    @Override
    public List<Event> findAll() {
        log.info("heat the service");
        return eventRepository.findAll();
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
    public boolean updateEvent(Integer id, Event event) {
        Event requestedEvent = this.eventRepository.findById(id).orElse(null);
        if (requestedEvent==null){
            log.info("il n'ya aucun event avec l'id={}",id);
            return Boolean.FALSE;
        }
        this.eventRepository.save(event);
        return Boolean.TRUE;
    }


}
