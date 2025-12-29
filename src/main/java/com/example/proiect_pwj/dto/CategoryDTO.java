package com.example.proiect_pwj.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class CategoryDTO {
    @NotBlank(message = "Numele categoriei este obligatoriu")
    private String name;
}