package com.chewie.repositories;

import com.chewie.domain.Booking;
import com.chewie.domain.BookingType;
import com.chewie.domain.User;
import com.chewie.repositories.BookingRepository;
import liquibase.pro.packaged.bo;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestConstructor;

import javax.transaction.Transactional;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

    @AfterEach
    void after(){
        bookingRepository.deleteAll();
        lastInsertId=0;
    }
}