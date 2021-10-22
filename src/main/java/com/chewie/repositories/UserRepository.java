package com.chewie.repositories;

import com.chewie.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;



public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Save a given User
     * 
     * @param user must not be null
     * @return the saved user
     */
    User save(User user);

    /**
     * Retrieves a User by its id
     * 
     * @param id must not be null
     * @return the User with the given id or empty
     */
    Optional<User> findById(Long id);
}