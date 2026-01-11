package com.example.proiect_pwj.service;

import com.example.proiect_pwj.config.UserSession;
import com.example.proiect_pwj.dto.ReservationDTO;
import com.example.proiect_pwj.dto.ReservationResponseDTO;
import com.example.proiect_pwj.model.Event;
import com.example.proiect_pwj.model.User;
import com.example.proiect_pwj.repository.EventRepository;
import com.example.proiect_pwj.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock private ReservationRepository reservationRepository;
    @Mock private EventRepository eventRepository;
    @Mock private UserSession userSession;

    @InjectMocks private ReservationService reservationService;

    @Test
    void whenValidReservation_thenSeatsShouldDecrease() {
        String token = "mock-token";
        User mockUser = new User();
        mockUser.setId(1L);

        Event mockEvent = new Event();
        mockEvent.setId(100L);
        mockEvent.setAvailableSeats(10);
        mockEvent.setTicketPrice(50.0);
        mockEvent.setDateTime(LocalDateTime.now().plusDays(5));

        ReservationDTO dto = new ReservationDTO();
        dto.setEventId(100L);
        dto.setNumberOfTickets(3);

        when(userSession.getUser(token)).thenReturn(mockUser);
        when(eventRepository.findById(100L)).thenReturn(Optional.of(mockEvent));
        when(reservationRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        ReservationResponseDTO response = reservationService.createReservation(dto, token);

        assertEquals(7, mockEvent.getAvailableSeats()); // 10 - 3 = 7
        assertEquals(150.0, response.getTotalPrice()); // 3 * 50 = 150
        verify(eventRepository, times(1)).save(mockEvent);
    }

    @Test
    void whenNotEnoughSeats_thenThrowException() {
        Event mockEvent = new Event();
        mockEvent.setAvailableSeats(2);
        mockEvent.setDateTime(LocalDateTime.now().plusDays(1));

        ReservationDTO dto = new ReservationDTO();
        dto.setEventId(1L);
        dto.setNumberOfTickets(5);

        when(userSession.getUser(anyString())).thenReturn(new User());
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(mockEvent));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(dto, "token");
        });

        assertTrue(exception.getMessage().contains("Nu sunt destule locuri"));
    }
}