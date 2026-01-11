package com.example.proiect_pwj.service;

import com.example.proiect_pwj.config.UserSession;
import com.example.proiect_pwj.dto.ReviewDTO;
import com.example.proiect_pwj.model.Event;
import com.example.proiect_pwj.model.User;
import com.example.proiect_pwj.repository.EventRepository;
import com.example.proiect_pwj.repository.ReservationRepository;
import com.example.proiect_pwj.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock private ReviewRepository reviewRepository;
    @Mock private ReservationRepository reservationRepository;
    @Mock private EventRepository eventRepository;
    @Mock private UserSession userSession;

    @InjectMocks private ReviewService reviewService;

    @Test
    void whenUserHasNoReservation_thenCannotLeaveReview() {
        String token = "token-test";
        User user = new User();
        Event pastEvent = new Event();
        pastEvent.setDateTime(LocalDateTime.now().minusDays(10)); // Eveniment trecut

        ReviewDTO dto = new ReviewDTO();
        dto.setEventId(1L);

        when(userSession.getUser(token)).thenReturn(user);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(pastEvent));
        when(reservationRepository.existsByUserAndEvent(user, pastEvent)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> reviewService.addReview(dto, token));
        assertTrue(ex.getMessage().contains("Doar persoanele care au rezervat"));
    }
}