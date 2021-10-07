package com.chewie;

import com.chewie.domain.Booking;
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
        booking.setType("A type");
        booking.setBookedOn(new Timestamp(java.lang.System.currentTimeMillis()));
        lastInsertId = bookingRepository.save(booking).getId();
    }

    @Test
    @DisplayName("find Booking by id")
    void testFindById() {
        var booking = bookingRepository.findById(lastInsertId);
        var condition = new Condition<Booking>(b -> "User".equals(b.getUser().getName()), "booking has user");
        assertThat(booking).isPresent();
        assertThat(booking).hasValueSatisfying(condition);
    }

    @AfterEach
    void after(){
        bookingRepository.deleteAll();
        lastInsertId=0;
    }
}