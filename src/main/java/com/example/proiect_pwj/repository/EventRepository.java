package com.example.proiect_pwj.repository;

import com.example.proiect_pwj.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByLocationId(Long locationId);
}