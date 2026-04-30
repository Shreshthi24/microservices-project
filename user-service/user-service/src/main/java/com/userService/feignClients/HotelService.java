package com.userService.feignClients;

import com.userService.external.HotelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("/hotels/{id}")
    HotelDTO getHotel(@PathVariable("id") String id);
}