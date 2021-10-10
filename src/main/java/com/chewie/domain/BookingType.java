package com.chewie.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Data
@Entity
public class BookingType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Boolean incoming;

    public static Builder builder = new Builder();

    private BookingType(){
        //Booking Types should not be created directly
    }


    public static final class Builder{
        private final BookingType toBuild = new BookingType();
        public BookingType incomingBooking(){
            toBuild.setIncoming(true);
            return toBuild;
        }


        public BookingType outcomingBooking() {
            toBuild.setIncoming(false);
            return toBuild;
        }
    }
}