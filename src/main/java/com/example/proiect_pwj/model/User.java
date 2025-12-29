package com.example.proiect_pwj.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numele este obligatoriu")
    private String fullName;

    @Email(message = "Email-ul trebuie să fie valid")
    @Column(unique = true)
    private String email;

    private String password;
}