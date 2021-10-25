package com.chewie.controllers;


import com.chewie.domain.Booking;
import com.chewie.repositories.BookingTypeRepository;
import com.chewie.services.BookingService;
import com.chewie.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "This is to perform an incoming booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Details of the Booking",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "User not found, no booking possible",
                    content = @Content)
    })
    @PostMapping("/booking_in/{id}")
    public Booking bookIncoming(@PathVariable(name = "id")Long user_id) {
        return bookingService.registerBooking(userService.findUserById(user_id), true);
    }
    @Operation(summary = "This is to perform an outcoming booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Details of the Booking",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "User not found, no booking possible",
                    content = @Content)
    })
    @PostMapping("/booking_out/{id}")
    public Booking bookOutcoming(@PathVariable(name = "id")Long user_id) {
        return bookingService.registerBooking(userService.findUserById(user_id),false);
    }

}
