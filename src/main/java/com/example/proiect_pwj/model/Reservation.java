package com.example.proiect_pwj.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Min(1)
    private int numberOfTickets;

    private LocalDateTime reservationDate;

    private double totalPrice;
}