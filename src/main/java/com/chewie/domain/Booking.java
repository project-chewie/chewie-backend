package com.chewie.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Data
@Entity
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Integer id;
    @JoinColumn(name = "fk_Type", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private BookingType type;
    @JoinColumn(name = "fK_User", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    private User user;
    @Column( nullable = false)
    private Timestamp bookedOn;

    public Booking(){
        this.bookedOn = new Timestamp(System.currentTimeMillis());
    }
}