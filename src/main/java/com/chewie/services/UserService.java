package com.chewie.services;

import com.chewie.domain.User;
import com.chewie.exceptions.UserNotFoundException;
import com.chewie.repositories.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User addUser(@NonNull String username, @NonNull String password){
        User newUser = new User();
        newUser.setName(username);
        newUser.setPassword(password);
        return userRepository.save(newUser);
    }

    public User findUserById(@NonNull Long user_id) {
        return userRepository.findById(user_id).orElseThrow(() -> new UserNotFoundException(user_id));
    }

    public List<User> findAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
