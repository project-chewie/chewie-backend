package com.chewie.repositories;

import com.chewie.domain.BookingType;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DirtiesContext
public class BookingTypesRepositoryTest {
    @Autowired
    private BookingTypeRepository bookingTypeRepository;




    @Test
    @DisplayName("Find all available Booking types, and verifies at least one incoming and one outcoming are present")
    void testVerifyMinimalMeaningfulSemantic() {
        var bookingTypes = bookingTypeRepository.findAll();
        var bookingType_is_incoming= new Condition<BookingType>(b -> Boolean.TRUE.equals(b.getIncoming()), "Booking has user");
        var bookingType_is_outcoming = new Condition<BookingType>(b -> Boolean.FALSE.equals(b.getIncoming()), "Booking has user");
        assertThat(bookingTypes).doesNotContainNull();
        assertThat(bookingTypes).areAtLeastOne(bookingType_is_incoming);
        assertThat(bookingTypes).areAtLeastOne(bookingType_is_outcoming);
    }

}