package com.example.springbootbackend.dto;


import lombok.Getter;
import  lombok.Setter;

@Getter
@Setter
public class SearchRequestDto {

    String column;
    String value;
    Operation operation;


    public enum Operation{
        EQUAL,
        LIKE,
        IN,
        GREATER_THAN,
        LESS_THAN,
        BETWEEN;
    }
}
