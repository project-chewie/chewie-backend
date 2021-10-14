package com.chewie.services;

import com.chewie.domain.Booking;
import com.chewie.domain.BookingType;
import com.chewie.domain.User;
import com.chewie.repositories.BookingRepository;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {

    @InjectMocks
    BookingService bookingService;

    @Mock
    BookingRepository bookingRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("service allows booking incoming  user ")
    public void testBookingsInInsertion(){
        var user = new User();
        Booking mockInBooking = new Booking();
        mockInBooking.setType(BookingType.builder.incomingBooking());
        mockInBooking.setUser(user);
        when(bookingRepository.save(any(Booking.class))).thenReturn(mockInBooking).thenReturn(mockInBooking);
        var bookingIn = bookingService.registerBooking(user, true);
        assertThat(bookingIn).isNotNull();
        var booking_has_user = new Condition<Booking>(b -> b.getUser()!=null, "Booking has any user");
        var booking_is_incoming = new Condition<Booking>(b -> b.getType().getIncoming(), "Booking is incoming");
        assertThat(bookingIn).is(booking_has_user);
        assertThat(bookingIn).is(booking_is_incoming);

    }

    @Test
    @DisplayName("service allows booking outcoming for user ")
    public void testBookingOutInsertion(){
        var user = new User();
        Booking mockOutBooking = new Booking();
        mockOutBooking.setType(BookingType.builder.outcomingBooking());
        mockOutBooking.setUser(user);
        when(bookingRepository.save(any(Booking.class))).thenReturn(mockOutBooking).thenReturn(mockOutBooking);
        var bookingIn = bookingService.registerBooking(user, false);
        assertThat(bookingIn).isNotNull();
        var booking_has_user = new Condition<Booking>(b -> b.getUser()!=null, "Booking has any user");
        assertThat(bookingIn).is(booking_has_user);
        var bookingOut = bookingService.registerBooking(user, false);
        Condition<Booking> compliantOutcomingBookingCondition = new Condition<Booking>(b -> !b.getType().getIncoming(), "Booking is outcoming");
        assertThat(bookingOut).is(compliantOutcomingBookingCondition);
    }
}
