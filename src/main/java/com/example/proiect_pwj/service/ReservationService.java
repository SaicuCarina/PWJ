package com.example.proiect_pwj.service;

import com.example.proiect_pwj.config.UserSession;
import com.example.proiect_pwj.dto.ReservationDTO;
import com.example.proiect_pwj.model.*;
import com.example.proiect_pwj.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired private ReservationRepository reservationRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private UserSession userSession;

    @Transactional
    public Reservation createReservation(ReservationDTO dto, String token) {
        User user = userSession.getUser(token);
        if (user == null) {
            throw new RuntimeException("Sesiune expirata! Te rugam sa te reloghezi.");
        }

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Eveniment negasit"));

        if (event.getAvailableSeats() < dto.getNumberOfTickets()) {
            throw new RuntimeException("Nu sunt destule locuri libere");
        }

        event.setAvailableSeats(event.getAvailableSeats() - dto.getNumberOfTickets());
        eventRepository.save(event);

        Reservation res = new Reservation();
        res.setUser(user);
        res.setEvent(event);
        res.setNumberOfTickets(dto.getNumberOfTickets());
        res.setReservationDate(LocalDateTime.now());
        res.setTotalPrice(dto.getNumberOfTickets() * event.getTicketPrice());

        return reservationRepository.save(res);
    }

    public List<ReservationDTO> getMyReservations(String token) {
        User user = userSession.getUser(token);
        if (user == null) throw new RuntimeException("Sesiune expirata!");

        List<Reservation> reservations = reservationRepository.findByUser(user);

        return reservations.stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    private ReservationDTO convertToDTO(Reservation res) {
        ReservationDTO dto = new ReservationDTO();
        dto.setEventId(res.getEvent().getId());
        dto.setNumberOfTickets(res.getNumberOfTickets());
        return dto;
    }
}