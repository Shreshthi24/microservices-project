package com.ratingService.controllers;

import com.ratingService.entity.Rating;
import com.ratingService.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingRepository repo;

    @PostMapping
    public Rating create(@RequestBody Rating rating) {
        return repo.save(rating);
    }

    @GetMapping("/user/{userId}")
    public List<Rating> getByUser(@PathVariable String userId) {
        return repo.findByUserId(userId);
    }
}