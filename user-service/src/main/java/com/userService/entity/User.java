package com.userService.entity;

import com.userService.external.entities.Rating;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    private String name;
    private String email;

    @Transient
    private List<Rating> ratings;
}