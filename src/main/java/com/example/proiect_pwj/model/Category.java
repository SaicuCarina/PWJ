package com.example.proiect_pwj.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numele categoriei este obligatoriu")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Event> events;
}