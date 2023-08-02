package com.example.springbootbackend.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonSerialize
public class UserLocationDto {
    private long id;
    private String email;
    private String place;
    private double longitude;
    private double latitude;
}
