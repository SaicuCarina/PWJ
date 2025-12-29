package com.example.proiect_pwj.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numele locației este obligatoriu")
    private String name;

    private String address;

    @Min(value = 1, message = "Capacitatea trebuie să mai mare decat zero")
    private int capacity;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Event> events;
}