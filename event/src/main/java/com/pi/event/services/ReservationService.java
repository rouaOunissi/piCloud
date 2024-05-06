package com.pi.event.services;

import com.pi.event.dto.ReservationDto;
import com.pi.event.entities.Event;
import com.pi.event.entities.Reservation;

import java.util.List;

public interface ReservationService {
    public Reservation save(Reservation reservation);

    public Reservation findById(Integer id);



    public List<Reservation> findAll();

    void delete(Integer id);

    public Reservation updateReservation(Reservation reservation);


    public int getNbrReservationByEvent(Integer idEvent);



    public boolean addReservationToEvent(Reservation reservation, Integer idEvent);




}
