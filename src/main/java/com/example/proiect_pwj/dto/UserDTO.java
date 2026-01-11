package com.example.proiect_pwj.dto;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
    @NotBlank(message = "Numele este obligatoriu")
    private String fullName;

    @Email(message = "Email-ul trebuie să fie valid")
    @NotBlank(message = "Email-ul este obligatoriu")
    private String email;

    @NotBlank(message = "Parola este obligatorie")
    @Size(min = 4, message = "Parola trebuie sa aiba cel putin 4 caractere")
    private String password;

}