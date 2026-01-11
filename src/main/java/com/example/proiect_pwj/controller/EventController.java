package com.example.proiect_pwj.controller;

import com.example.proiect_pwj.dto.EventDTO;
import com.example.proiect_pwj.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventDTO>> getUpcoming() { return ResponseEntity.ok(eventService.getUpcomingEvents()); }
}