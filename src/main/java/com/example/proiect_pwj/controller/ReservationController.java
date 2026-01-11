package com.example.proiect_pwj.controller;

import com.example.proiect_pwj.dto.ReservationDTO;
import com.example.proiect_pwj.dto.ReservationResponseDTO;
import com.example.proiect_pwj.model.Reservation;
import com.example.proiect_pwj.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationDTO dto, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(reservationService.createReservation(dto, token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my")
    public List<ReservationResponseDTO> getMyReservations(@RequestHeader("Authorization") String token) {
        return reservationService.getMyReservations(token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancel(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            reservationService.cancelReservation(id, token);
            return ResponseEntity.ok("Rezervarea cu ID-ul " + id + " a fost anulata cu succes. Locurile au fost eliberate.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}