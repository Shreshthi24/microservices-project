package com.hotelService.controllers;

import com.hotelService.dto.HotelDTO;
import com.hotelService.entity.Hotel;
import com.hotelService.service.HotelService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService service;

    // CREATE HOTEL
    @PostMapping
    public Hotel create(
            @Valid @RequestBody HotelDTO request) {

        return service.createHotel(request);
    }

    // GET HOTEL BY ID
    @GetMapping("/{id}")
    public Hotel get(@PathVariable String id) {

        return service.getHotel(id);
    }

    // GET ALL HOTELS
    @GetMapping
    public List<Hotel> getAllHotels() {

        return service.getAllHotels();
    }

    // SEARCH BY LOCATION
    @GetMapping("/location/{location}")
    public List<Hotel> getByLocation(
            @PathVariable String location) {

        return service.getByLocation(location);
    }

    // DELETE HOTEL
    @DeleteMapping("/{id}")
    public String deleteHotel(
            @PathVariable String id) {

        return service.deleteHotel(id);
    }
}