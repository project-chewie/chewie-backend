package com.chewie.repositories;

import com.chewie.domain.BookingType;
import org.springframework.data.repository.CrudRepository;


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