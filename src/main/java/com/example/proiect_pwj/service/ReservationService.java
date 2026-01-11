package com.example.proiect_pwj.service;

import com.example.proiect_pwj.config.UserSession;
import com.example.proiect_pwj.dto.ReservationDTO;
import com.example.proiect_pwj.dto.ReservationResponseDTO;
import com.example.proiect_pwj.model.*;
import com.example.proiect_pwj.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired private ReservationRepository reservationRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private UserSession userSession;

    @Transactional
    public ReservationResponseDTO createReservation(ReservationDTO dto, String token) {
        User user = userSession.getUser(token);
        if (user == null) {
            throw new RuntimeException("Sesiune expirata! Te rugam sa te reloghezi.");
        }

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Eveniment negasit"));

        if (event.getDateTime().isBefore(java.time.LocalDateTime.now())) {
            throw new RuntimeException("Nu poti rezerva locuri la un eveniment care a trecut deja!");
        }

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

        Reservation savedRes = reservationRepository.save(res);

        return convertToResponseDTO(savedRes);
    }

    public List<ReservationResponseDTO> getMyReservations(String token) {
        User user = userSession.getUser(token);
        if (user == null) throw new RuntimeException("Sesiune expirata!");

        List<Reservation> reservations = reservationRepository.findByUser(user);

        return reservations.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private ReservationResponseDTO convertToResponseDTO(Reservation res) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setId(res.getId());
        dto.setNumberOfTickets(res.getNumberOfTickets());
        dto.setTotalPrice(res.getTotalPrice());

        Event event = res.getEvent();
        dto.setEventId(event.getId());
        dto.setEventTitle(event.getTitle());
        dto.setEventDate(event.getDateTime().toString());

        if (event.getLocation() != null) {
            dto.setLocationName(event.getLocation().getName());
        }

        return dto;
    }

    private ReservationDTO convertToDTO(Reservation res) {
        ReservationDTO dto = new ReservationDTO();
        dto.setEventId(res.getEvent().getId());
        dto.setNumberOfTickets(res.getNumberOfTickets());
        return dto;
    }

    @Transactional
    public void cancelReservation(Long id, String token) {
        User user = userSession.getUser(token);
        if (user == null) throw new RuntimeException("Sesiune expirata!");

        Reservation res = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezervarea nu a fost gasita"));

        if (!res.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Nu poti anula rezervarea altcuiva!");
        }

        if (res.getEvent().getDateTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Nu poti anula o rezervare pentru un eveniment care a trecut deja!");
        }

        Event event = res.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + res.getNumberOfTickets());
        eventRepository.save(event);

        reservationRepository.delete(res);
    }
}