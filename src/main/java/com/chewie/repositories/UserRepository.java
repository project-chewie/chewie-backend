package com.chewie.repositories;
import java.util.Optional;

import com.chewie.domain.User;

import org.springframework.data.repository.CrudRepository;



public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * Save a given User
     * 
     * @param user must not be null
     * @return the saved user
     */
    User save(User user);

    /**
     * Retrieves a User by it's id
     * 
     * @param id must not be null
     * @return the User with the given id or empty
     */
    Optional<User> findById(Integer id);
}