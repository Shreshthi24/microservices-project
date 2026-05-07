package com.hotelService.service;


import com.hotelService.dto.HotelDTO;
import com.hotelService.entity.Hotel;
import com.hotelService.repositories.HotelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository repo;

    // CREATE HOTEL
    public Hotel createHotel(HotelDTO request) {

        if(repo.existsByName(request.getName())) {
            throw new RuntimeException("Hotel already exists");
        }

        Hotel hotel = new Hotel();

        hotel.setName(request.getName());
        hotel.setLocation(request.getLocation());

        return repo.save(hotel);
    }

    // GET HOTEL BY ID
    public Hotel getHotel(String id) {

        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hotel not found"));
    }

    // GET ALL HOTELS
    public List<Hotel> getAllHotels() {

        return repo.findAll();
    }

    // SEARCH BY LOCATION
    public List<Hotel> getByLocation(String location) {

        return repo.findByLocation(location);
    }

    // DELETE HOTEL
    public String deleteHotel(String id) {

        Hotel hotel = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hotel not found"));

        repo.delete(hotel);

        return "Hotel deleted successfully";
    }
}