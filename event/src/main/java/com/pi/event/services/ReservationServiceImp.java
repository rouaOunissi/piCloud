package com.pi.event.services;

import com.pi.event.entities.Event;
import com.pi.event.entities.Reservation;
import com.pi.event.exceptions.EntityNotFoundException;
import com.pi.event.exceptions.ErrorCodes;
import com.pi.event.exceptions.InvalidEntityException;
import com.pi.event.repositories.EventRepository;
import com.pi.event.repositories.ReservationRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Data
@RequiredArgsConstructor

public class ReservationServiceImp implements ReservationService{

    private final ReservationRepository reservationRepository ;
    private final EventRepository eventRepository;

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation == null) {
            log.error("Reservation is not valid {}", reservation);
            throw new InvalidEntityException("La Reservation n'est pas valide", ErrorCodes.RESERVATION_NOT_VALID);
        }
        return this.reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Integer id) {
        if (id == null) {
            log.error("Reservation ID is null");
            return null;
        }
        return this.reservationRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune Reservation avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.RESERVATION_NOT_FOUND)
        );
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Reservation ID is null");
            return;
        }
        // check if there is associaion
        this.reservationRepository.deleteById(id);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        if (reservation == null) {
            log.error("Event is not valid {}", reservation);
            throw new InvalidEntityException("L'Event n'est pas valide", ErrorCodes.EVENT_NOT_VALID);
        }
        return this.reservationRepository.save(reservation);
    }

    @Override
    public int getNbrReservationByEvent(Integer idEvent) {
        Event requestedEvent = this.eventRepository.findById(Math.toIntExact(idEvent)).orElse(null);
        if (requestedEvent==null){
            log.info("Event introuvable dans la base");
            return -1;
        }
        return this.reservationRepository.countByEvent_IdEvent(idEvent);
    }

    @Override
    public void addReservationToEvent(Reservation reservation, Integer idEvent) {
        if (reservation!=null && idEvent!=null){
            Event requestedEvent = this.eventRepository.findById(idEvent).orElse(null);
            if (requestedEvent!=null){
                reservation.setEvent(requestedEvent);
                this.reservationRepository.save(reservation);
            }
        }
    }
}
