package com.chewie.controllers;


import com.chewie.domain.Booking;
import com.chewie.repositories.BookingTypeRepository;
import com.chewie.services.BookingService;
import com.chewie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

@RestController
public class BookingController {

    @Autowired
    UserService userService;
    @Autowired
    private BookingService bookingService;


    @PostMapping("/users/{id}/booking_in")
    public Booking bookIncoming(@PathVariable(name = "id")Long user_id) {
        return bookingService.registerBooking(userService.findUserById(user_id), true);
    }

    @PostMapping("/users/{id}/booking_out")
    public Booking bookOutcoming(@PathVariable(name = "id")Long user_id) {
        return bookingService.registerBooking(userService.findUserById(user_id),false);
    }

}
