package com.hotelService.controllers;

import com.hotelService.entity.Hotel;
import com.hotelService.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelRepository repo;

    @PostMapping
    public Hotel create(@RequestBody Hotel hotel) {
        return repo.save(hotel);
    }

    @GetMapping("/{id}")
    public Hotel get(@PathVariable String id) {
        return repo.findById(id).orElseThrow();
    }



}