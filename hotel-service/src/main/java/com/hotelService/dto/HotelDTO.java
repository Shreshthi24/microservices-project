package com.hotelService.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {


        @NotBlank(message = "Hotel name is required")
        @Size(min = 3, max = 100,
                message = "Hotel name must be between 3 and 100 characters")
        private String name;

        @NotBlank(message = "Location is required")
        @Size(min = 2, max = 100,
                message = "Location must be between 2 and 100 characters")
        private String location;
    }

