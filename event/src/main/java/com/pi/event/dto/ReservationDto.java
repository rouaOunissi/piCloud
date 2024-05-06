package com.pi.event.dto;

import com.pi.event.entities.Reservation;
import lombok.Data;

@Data
public class ReservationDto {

    private Reservation reservation;
    private Integer idEvent;
    private Integer idUser;
    }
