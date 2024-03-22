package com.pi.event.repositories;
import com.pi.event.entities.Event;
import com.pi.event.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {


    public int countByEvent_IdEvent(Integer idEvent);

    public List<Reservation> getReservationsByEvent(Event event);

}
