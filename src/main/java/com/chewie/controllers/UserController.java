package com.chewie.controllers;


import com.chewie.domain.Booking;
import com.chewie.domain.User;
import com.chewie.services.BookingService;
import com.chewie.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    @Operation(summary = "This is to find all available user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Details of a user",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "User not found, no booking possible",
                    content = @Content)
    })
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAllUsers();
    }
    @Operation(summary = "This is to find a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Details of all user",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "User not found, no booking possible",
                    content = @Content)
    })
    @GetMapping("/user/{id}")
    public User findUser(@PathVariable(name = "id")Long user_id) {
        return userService.findUserById(user_id);
    }

}
