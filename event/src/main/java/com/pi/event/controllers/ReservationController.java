package com.pi.event.controllers;

import com.pi.event.dto.ReservationDto;
import com.pi.event.entities.Reservation;
import com.pi.event.exceptions.EntityNotFoundException;
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

    public ResponseEntity<?> save(@RequestBody Reservation reservation){
        if (reservation==null)
            return new ResponseEntity<>("input not valid",HttpStatus.BAD_REQUEST);
        if (reservationService.save(reservation)==null) {
            return new ResponseEntity<>("reservation not created", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(reservationService.save(reservation),HttpStatus.CREATED);
        }
    }

    @GetMapping(value = "/reservations/{idReservation}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable("idReservation") Integer id){
        if (id==null){
            return new ResponseEntity<>("id not valable",HttpStatus.BAD_REQUEST);
        }
        Reservation requestedreservation = this.reservationService.findById(id);
        if (requestedreservation==null){
            return new ResponseEntity<>("reservation not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requestedreservation,HttpStatus.FOUND);
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
    public ResponseEntity<?> updateReservation(@RequestBody Reservation reservation){
        if (reservation==null)
            return new ResponseEntity<>("Provided input not valable",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(this.reservationService.updateReservation(reservation),HttpStatus.OK);
    }



    @PostMapping("/reservations/addReservationToEvent")
    public ResponseEntity<?> addReservationToEvent(@RequestBody  ReservationDto reservationDto) {
        if (reservationDto == null)
            return new ResponseEntity<>("provided input is null", HttpStatus.BAD_REQUEST);
       boolean succes = this.reservationService.addReservationToEvent(reservationDto.getReservation(), reservationDto.getIdEvent());
       if (succes)
           return new ResponseEntity<>("Reservation Created", HttpStatus.CREATED);
       return new ResponseEntity<>("Reservation Not Created",HttpStatus.NOT_FOUND);
    }
}
