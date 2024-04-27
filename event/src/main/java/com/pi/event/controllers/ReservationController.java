package com.pi.event.controllers;

import com.pi.event.dto.ReservationDto;
import com.pi.event.entities.Reservation;
import com.pi.event.repositories.ReservationRepository;
import com.pi.event.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService ;


    @PostMapping(value = "/reservations/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    public Reservation save(@RequestBody Reservation reservation){
        return this.reservationService.save(reservation);
    }

    @GetMapping(value = "/reservations/{idReservation}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation findById(@PathVariable("idReservation") Integer id){
        return this.reservationService.findById(id);
    }


    @GetMapping(value = "/reservations/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> findAll(){
        return this.reservationService.findAll();
    }


    @DeleteMapping(value =  "/reservations/delete/{idReservation}")
    public void delete(@PathVariable("idReservation") Integer id){
        this.reservationService.delete(id);
    }

    @PutMapping(value = "/reservations/update")
    public Reservation updateReservation(@RequestBody Reservation reservation){
        return this.reservationService.updateReservation(reservation);
    }



    @PostMapping("/reservations/addReservationToEvent")
    public ResponseEntity<?> addReservationToEvent(@RequestBody  ReservationDto reservationDto){
        if (reservationDto == null)
            return new ResponseEntity<>("Reservation not Created", HttpStatus.BAD_REQUEST);

        reservationService.addReservationToEvent(reservationDto.getReservation(), reservationDto.getIdEvent());
        return new ResponseEntity<>(reservationDto,HttpStatus.CREATED);

    }
}
