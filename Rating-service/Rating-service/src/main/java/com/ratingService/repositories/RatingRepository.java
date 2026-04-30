package com.ratingService.repositories;

import com.ratingService.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String> {

    // by userId
    List<Rating> findByUserId(String userId);

    // hotelId
    List<Rating> findByHotelId(String hotelId);
}