package com.pi.event.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvent ;

    private String eventName ;

    private String eventDescription ;

    private int eventNbplace ;
    //private int idTuteor ;
    private double  eventPrice ;

    @CreatedDate
    @Column(name = "creationDate", nullable = false, updatable = false)
    private Instant creationDate;

    @LastModifiedDate
    @Column(name = "lastModifiedDate")
    private Instant lastModifiedDate;


    @DateTimeFormat(pattern = "dd MMMM yyyy")
    private LocalDate planifiedDate ;

    @JsonIgnore
    @OneToMany(mappedBy = "event" , cascade = CascadeType.ALL)
    private List<Reservation> reservations ;


}
