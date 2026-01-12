package com.example.proiect_pwj.service;

import com.example.proiect_pwj.config.UserSession;
import com.example.proiect_pwj.dto.ReviewDTO;
import com.example.proiect_pwj.model.Event;
import com.example.proiect_pwj.model.Review;
import com.example.proiect_pwj.model.User;
import com.example.proiect_pwj.repository.EventRepository;
import com.example.proiect_pwj.repository.ReservationRepository;
import com.example.proiect_pwj.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private ReservationRepository reservationRepository;
    @Autowired private UserSession userSession;

    public ReviewDTO addReview(ReviewDTO dto, String token) {
        User user = userSession.getUser(token);
        if (user == null) {
            throw new RuntimeException("Trebuie să fii logat pentru a lasa un review!");
        }

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Evenimentul nu exista"));

        if (event.getDateTime().isAfter(java.time.LocalDateTime.now())) {
            throw new RuntimeException("Nu poți lasa un review pentru un eveniment care nu a avut loc inca!");
        }

        boolean hasReservation = reservationRepository.existsByUserAndEvent(user, event);
        if (!hasReservation) {
            throw new RuntimeException("Doar persoanele care au rezervat bilete pot lasa un review!");
        }

        Review review = new Review();
        review.setUser(user);
        review.setEvent(event);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        Review savedReview = reviewRepository.save(review);

        ReviewDTO responseDTO = new ReviewDTO();
        responseDTO.setEventId(savedReview.getEvent().getId());
        responseDTO.setRating(savedReview.getRating());
        responseDTO.setComment(savedReview.getComment());

        return responseDTO;
    }
    public List<ReviewDTO> getReviewsByEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Eveniment negasit"));

        List<Review> reviews = reviewRepository.findByEvent(event);

        return reviews.stream().map(review -> {
            ReviewDTO dto = new ReviewDTO();
            dto.setEventId(review.getEvent().getId());
            dto.setRating(review.getRating());
            dto.setComment(review.getComment());

            return dto;
        }).collect(java.util.stream.Collectors.toList());
    }
}
