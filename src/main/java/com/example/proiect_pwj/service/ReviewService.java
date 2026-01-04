package com.example.proiect_pwj.service;

import com.example.proiect_pwj.config.UserSession;
import com.example.proiect_pwj.dto.ReviewDTO;
import com.example.proiect_pwj.model.Event;
import com.example.proiect_pwj.model.Review;
import com.example.proiect_pwj.model.User;
import com.example.proiect_pwj.repository.EventRepository;
import com.example.proiect_pwj.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private UserSession userSession;

    public Review addReview(ReviewDTO dto, String token) {
        User user = userSession.getUser(token);

        if (user == null) {
            throw new RuntimeException("Sesiune expirată!");
        }

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Eveniment negăsit"));

        Review review = new Review();
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setEvent(event);

        review.setUser(user);

        return reviewRepository.save(review);
    }

    public List<ReviewDTO> getReviewsByEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Eveniment negăsit"));

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
