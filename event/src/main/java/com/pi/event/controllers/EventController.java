package com.pi.event.controllers;


import com.pi.event.entities.Event;
import com.pi.event.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService ;
    @PostMapping(value = "/events/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Event save(@RequestBody Event event){
        return this.eventService.save(event);
    }

    @GetMapping(value = "/events/{idEvent}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Event findById(@PathVariable("idEvent") Integer id){
        return this.eventService.findEventById(id);
    }


    @GetMapping(value = "/events/filter/{nameEvent}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Event findByNameEvent(@PathVariable("nameEvent") String nameEvent){
        return this.eventService.findByNameEvent(nameEvent);
    }


    @GetMapping(value = "/events/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> findAll(){
        return this.eventService.findAll();
    }

    @DeleteMapping(value =  "/events/delete/{idEvent}")
    void delete(@PathVariable("idEvent") Integer id){
        this.eventService.delete(id);
    }


    @PutMapping(value = "/events/update")
    public Event updateEvent(@RequestBody Event event){
        return this.eventService.updateEvent(event);
    }

}
