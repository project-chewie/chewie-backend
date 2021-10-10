package com.chewie.repositories;

import com.chewie.domain.Booking;
import com.chewie.domain.BookingType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface BookingTypeRepository extends CrudRepository<BookingType, Integer> {
    /**
     * Save a given BookingType
     * 
     * @param bookingType must not be null
     * @return the saved booking type
     */
    BookingType save(BookingType bookingType);

    /**
     * Retrieves all BookingTypes
     *
     * @return all BookingTypes in DB or an empty {@link Iterable}
     */
    Iterable<BookingType> findAll();
}