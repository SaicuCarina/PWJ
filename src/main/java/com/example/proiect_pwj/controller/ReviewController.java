package com.example.proiect_pwj.controller;

import com.example.proiect_pwj.dto.ReviewDTO;
import com.example.proiect_pwj.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO dto, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(reviewService.addReview(dto, token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<ReviewDTO>> getByEvent(@PathVariable Long eventId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByEvent(eventId);
        return ResponseEntity.ok(reviews);
    }
}