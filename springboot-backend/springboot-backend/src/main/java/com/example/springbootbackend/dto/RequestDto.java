package com.example.springbootbackend.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDto {

//   private SearchRequestDto searchRequestDto;
   private List<SearchRequestDto> searchRequestDto;

   private GlobalOperator globalOperator;

   public enum GlobalOperator{
      AND,OR;
   }


}
