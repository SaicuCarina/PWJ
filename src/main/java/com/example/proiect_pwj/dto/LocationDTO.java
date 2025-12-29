package com.example.proiect_pwj.dto;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class LocationDTO {
    @NotBlank(message = "Numele locatiei este obligatoriu")
    private String name;

    private String address;

    @Min(1)
    private int capacity;
}