package com.example.proiect_pwj.controller;

import com.example.proiect_pwj.dto.ReservationDTO;
import com.example.proiect_pwj.model.Reservation;
import com.example.proiect_pwj.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<String> makeReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            Reservation res = reservationService.createReservation(reservationDTO);
            return ResponseEntity.ok("Rezervare creata cu succes! ID: " + res.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}