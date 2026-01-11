package com.example.proiect_pwj.dto;

import lombok.Data;

@Data
public class ReservationResponseDTO {
    private Long id;
    private int numberOfTickets;
    private double totalPrice;
    private String eventTitle;
    private String eventDate;
    private String locationName;
    private Long eventId;
}