package com.example.proiect_pwj.controller;

import com.example.proiect_pwj.dto.EventDTO;
import com.example.proiect_pwj.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
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
}