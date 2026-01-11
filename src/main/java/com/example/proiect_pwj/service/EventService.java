package com.example.proiect_pwj.service;

import com.example.proiect_pwj.dto.EventDTO;
import com.example.proiect_pwj.model.Event;
import com.example.proiect_pwj.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evenimentul nu a fost gasit"));
        return convertToDTO(event);
    }

    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setTitle(event.getTitle());
        dto.setDateTime(event.getDateTime());
        dto.setTicketPrice(event.getTicketPrice());

        if (event.getLocation() != null) {
            dto.setLocationName(event.getLocation().getName());
        }

        if (event.getCategory() != null) {
            dto.setCategoryName(event.getCategory().getName());
        }

        return dto;
    }

    public List<EventDTO> getUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<Event> events = eventRepository.findByDateTimeAfterOrderByDateTimeAsc(now);

        return events.stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<EventDTO> searchEvents(String category, String location) {
        String cat = (category != null) ? category : "";
        String loc = (location != null) ? location : "";

        List<Event> events = eventRepository.findByCategoryNameContainingAndLocationNameContaining(cat, loc);

        if (events.isEmpty()) {
            throw new RuntimeException("Nu a fost gasit niciun eveniment pentru criteriile selectate.");
        }

        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}