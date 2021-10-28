package com.chewie.repositories;

import com.chewie.domain.Booking;
import com.chewie.domain.BookingType;
import com.chewie.domain.User;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestConstructor;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DirtiesContext
public class BookingsRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private  TestEntityManager em;

    private Integer lastInsertId;

    @BeforeEach
    void before() {
        var user = new User();
        user.setName("User");
        user.setPassword("Password");
        var booking = new Booking();
        em.persist(user);
        booking.setUser(user);
        BookingType bookingType = em.find(BookingType.class,1);
        booking.setType(bookingType);
        booking.setBookedOn(new Timestamp(java.lang.System.currentTimeMillis()));
        lastInsertId = bookingRepository.save(booking).getId();
    }

    @Test
    @DisplayName("find Booking by id")
    void testFindById() {
        var booking = bookingRepository.findById(lastInsertId);
        var booking_has_user = new Condition<Booking>(b -> "User".equals(b.getUser().getName()), "Booking has user");
        var booking_is_incoming = new Condition<Booking>(b -> b.getType().getIncoming(), "Booking is incoming");
        assertThat(booking).isPresent();
        assertThat(booking).hasValueSatisfying(booking_has_user);
        assertThat(booking).hasValueSatisfying(booking_is_incoming);
    }

/*
    @Test
    @DisplayName("find last 2 bookings for user")
    void testFindForUser() {
        Booking[] bookings = bookingRepository.findLastBookingsForUser(Integer.toUnsignedLong(lastInsertId));
        var booking_has_user = new Condition<Booking>(b -> "User".equals(b.getUser().getName()), "Booking has user");
        var booking_is_incoming = new Condition<Booking>(b -> b.getType().getIncoming(), "Booking is incoming");
        assertThat(bookings[0]).has(booking_has_user);
        assertThat(bookings[0]).is(booking_is_incoming);

        assertThat(bookings[1]).has(booking_has_user);
        assertThat(bookings[1]).isNot(booking_is_incoming);
    }
*/
    @AfterEach
    void after(){
        bookingRepository.deleteById(lastInsertId);
        lastInsertId=1;
    }
}