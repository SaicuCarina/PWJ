package com.example.proiect_pwj.service;

import com.example.proiect_pwj.dto.ReservationDTO;
import com.example.proiect_pwj.model.Event;
import com.example.proiect_pwj.model.Reservation;
import com.example.proiect_pwj.model.User;
import com.example.proiect_pwj.repository.EventRepository;
import com.example.proiect_pwj.repository.ReservationRepository;
import com.example.proiect_pwj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Reservation createReservation(ReservationDTO dto) {
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Evenimentul nu exista"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu exista"));

        if (event.getAvailableSeats() < dto.getNumberOfTickets()) {
            throw new RuntimeException("Nu mai sunt suficiente locuri! Locuri ramase: " + event.getAvailableSeats());
        }

        event.setAvailableSeats(event.getAvailableSeats() - dto.getNumberOfTickets());
        eventRepository.save(event);

        Reservation reservation = new Reservation();
        reservation.setEvent(event);
        reservation.setUser(user);
        reservation.setNumberOfTickets(dto.getNumberOfTickets());
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setTotalPrice(dto.getNumberOfTickets() * event.getTicketPrice());

        return reservationRepository.save(reservation);
    }
}