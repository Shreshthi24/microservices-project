package com.hotelService.repositories;

import com.hotelService.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository
        extends JpaRepository<Hotel, String> {

    Optional<Hotel> findByName(String name);

    List<Hotel> findByLocation(String location);

    boolean existsByName(String name);
}