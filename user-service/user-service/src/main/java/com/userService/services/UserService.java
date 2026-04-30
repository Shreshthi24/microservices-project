package com.userService.services;

import com.ratingService.entity.Rating;
import com.userService.entity.User;
import com.userService.feignClients.HotelService;
import com.userService.feignClients.RatingService;
import com.userService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private HotelService hotelService;

    public User getUser(String userId) {

        // ✅ Handle user not found
        User user = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<Rating> ratings = new ArrayList<>();

        // ✅ Call Rating Service safely
        try {
            ratings = ratingService.getRatings(userId);
        } catch (Exception e) {
            System.out.println("Rating Service Error: " + e.getMessage());
        }

        // ✅ Call Hotel Service safely
        for (Rating r : ratings) {
            try {
                r.setHotel(hotelService.getHotel(r.getHotelId()));
            } catch (Exception e) {
                System.out.println("Hotel Service Error: " + e.getMessage());
            }
        }

        user.setRatings(ratings);

        return user;
    }

    // ✅ FIXED METHOD
    public User save(User user) {
        return repo.save(user);
    }
}