package com.ratingService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ratingId;

    private String userId;   // ✅ MUST ADD THIS
    private String hotelId;
    private int rating;
    private String feedback;

    @Transient
    private Object hotel;   // avoid direct dependency
}