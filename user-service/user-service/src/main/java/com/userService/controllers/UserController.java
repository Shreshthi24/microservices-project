package com.userService.controllers;

import com.userService.entity.User;
import com.userService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    // ✅ CREATE USER
    @PostMapping
    public User create(@RequestBody User user) {
        return service.save(user);
    }

    // ✅ GET USER BY ID
    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return service.getUser(id);
    }
}