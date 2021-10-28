package com.chewie.repositories;

import com.chewie.domain.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface BookingRepository extends CrudRepository<Booking, Integer> {
    /**
     * Save a given Booking
     * 
     * @param booking must not be null
     * @return the saved booking
     */
    Booking save(Booking booking);

    /**
     * Retrieves a Booking by it's id
     * 
     * @param id must not be null
     * @return the Booking with the given id or empty
     */
    Optional<Booking> findById(Integer id);
/*
    @Query("SELECT TOP 2 u FROM BOOKING u WHERE u.user.id = :id ORDER BY id DESC")
    Booking[] findLastBookingsForUser(
            @Param("id") Long user_id
            );
            */

}