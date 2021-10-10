package com.chewie.services;

import com.chewie.domain.Booking;
import com.chewie.domain.BookingType;
import com.chewie.domain.User;
import com.chewie.repositories.BookingRepository;
import com.chewie.repositories.BookingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.StreamSupport;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingTypeRepository bookingTypeRepository;

    private BookingType incoming;
    private BookingType outcoming;

    @PostConstruct
    private void verifyBookingTypes(){
        verifyMinimalSemantic();
    }


    public Booking registerBooking(User user, Boolean isIncoming){
        Booking newBooking = new Booking();
        newBooking.setUser(user);
        newBooking.setType(outcoming);
        if(isIncoming) {
            newBooking.setType(incoming);
        }
        return bookingRepository.save(newBooking);
    }

    /**
     * Makes sure that no silly thing are manually done to the database
     * Maybe kind of too defensive, but whatever... We need to have at least one type of booking for incoming and
     * at least one for outcoming.
     *
     */
    private void verifyMinimalSemantic() {
        Iterable<BookingType> types = bookingTypeRepository.findAll();
        var hasAtLeastOneIncoming=StreamSupport.stream(types.spliterator(), false)
                .anyMatch(bookingType -> Boolean.TRUE.equals(bookingType.getIncoming()));
        Boolean myIncoming;
        if(!hasAtLeastOneIncoming){
            incoming=bookingTypeRepository.save(BookingType.builder.incomingBooking());
        }else {
            incoming = findBookingType(types, true);
        }
        var hasAtLeastOneOutcoming=StreamSupport.stream(types.spliterator(), false)
                .anyMatch(bookingType -> Boolean.FALSE.equals(bookingType.getIncoming()));
        if(!hasAtLeastOneOutcoming){
            outcoming=bookingTypeRepository.save(BookingType.builder.outcomingBooking());
        }else {
            outcoming = findBookingType(types, false);

        }
    }

    private BookingType findBookingType(Iterable<BookingType> types, boolean isIncoming) {
        return StreamSupport.stream(types.spliterator(), false)
                .filter(
                        bookingType -> isIncoming ==bookingType.getIncoming()
                )
                .findFirst().
                get();
    }


}
