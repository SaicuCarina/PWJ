package com.example.proiect_pwj.dto;

import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ReviewDTO {
    private Long eventId;
    @Min(1) @Max(5)
    private int rating;
    private String comment;
}