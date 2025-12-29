package com.example.proiect_pwj.dto;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {
    @NotBlank(message = "Numele este obligatoriu")
    private String fullName;

    @Email(message = "Email-ul trebuie să fie valid")
    @NotBlank(message = "Email-ul este obligatoriu")
    private String email;

}