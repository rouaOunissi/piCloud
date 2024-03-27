package com.pi.event.controllers;


import com.pi.event.entities.Event;
import com.pi.event.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {

    private final EventService eventService ;
    @PostMapping(value = "/events/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody Event event){
        if (event==null){
            return new ResponseEntity<>("event null",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.eventService.save(event),HttpStatus.CREATED);
    }

    @GetMapping(value = "/events/{idEvent}", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> findById(@PathVariable("idEvent") Integer id){
        if (id == null){
            return new ResponseEntity<>("id is null", HttpStatus.BAD_REQUEST);
        }
        Event requestedevent = this.eventService.findEventById(id);
        if (requestedevent==null){
            return new ResponseEntity<>("event not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requestedevent, HttpStatus.FOUND);
    }


    @GetMapping(value = "/events/filter/{nameEvent}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByNameEvent(@PathVariable("nameEvent") String nameEvent){
        if(nameEvent==null){
            return new ResponseEntity<>("provided input is null",HttpStatus.BAD_REQUEST);
        }
        Event requestedEvent = this.eventService.findByNameEvent(nameEvent);
        if (requestedEvent==null){
            return new ResponseEntity<>("event not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.eventService.findByNameEvent(nameEvent), HttpStatus.FOUND);
    }


    @GetMapping(value = "/events/all")
    public List<Event> findAll(){
        log.info("heat the api");
        return this.eventService.findAll();
    }

    @DeleteMapping(value =  "/events/delete/{idEvent}")
    public void delete(@PathVariable("idEvent") Integer id){
        this.eventService.delete(id);
    }


    @PutMapping("/events/update/{id}")
    public ResponseEntity<Void> updateEvent( @PathVariable("id") Integer id , @RequestBody Event event)  {
            boolean succes = eventService.updateEvent(id,event);
            if(succes){
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
