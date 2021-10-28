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


    @Query("select MAX(b.id) from Booking b where b.user.id = :user_id")
    public Booking findLastBookingsForUser(@Param("user_id") Long user_id);

/*
    @Query("SELECT FROM Booking b WHERE b.id = :id ")
    Booking findLastBookingsForUser(
            @Param("id") Long user_id
            );

 */

    /*
    public Booking[] findLastBookingsForUser(Long id) {
        Query jpqlQuery = getEntityManager().createQuery("SELECT b FROM Booking b WHERE b.user.id = :id");
        jpqlQuery.setParameter("id", id);
        return (UserEntity) jpqlQuery.getSingleResult();
    }
    */
}