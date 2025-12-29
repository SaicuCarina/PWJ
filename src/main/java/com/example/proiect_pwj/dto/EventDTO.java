package com.example.proiect_pwj.dto;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventDTO {

    @NotBlank(message = "Titlul nu poate fi gol")
    private String title;

    private String description;

    @Future(message = "Data trebuie sa fie mai mare ca data de astazi")
    private LocalDateTime dateTime;

    @Min(value = 1, message = "Prețul trebuie să fie mai mare decat zero")
    private double ticketPrice;

    @NotNull(message = "ID-ul locației este obligatoriu")
    private Long locationId;

    @NotNull(message = "ID-ul categoriei este obligatoriu")
    private Long categoryId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}