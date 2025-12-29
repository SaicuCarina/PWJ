package com.example.proiect_pwj.dto;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ReservationDTO {
    @NotNull(message = "ID-ul utilizatorului este obligatoriu")
    private Long userId;

    @NotNull(message = "ID-ul evenimentului este obligatoriu")
    private Long eventId;

    @Min(value = 1, message = "Trebuie sa rezervați cel putin un bilet")
    private int numberOfTickets;
}