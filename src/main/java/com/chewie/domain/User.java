package com.chewie.domain;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvNumber;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


@Data
@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @CsvBindByPosition(position = 0)
    @CsvNumber("10")
    private Long id;
    @Column(unique = true)
    @CsvBindByPosition(position = 1)
    private String name;
    @Column(nullable = false)
    @CsvBindByPosition(position = 2)
    private String password;
    @CsvBindByPosition(position = 3)
    private boolean isActive;

    public User(){
        this.isActive = true;
    }

    public User withUserName(@NonNull String username) {
        this.setName(username);
        return this;
    }

    public User withPassword(@NonNull String password) {
        this.setPassword(password);
        return this;
    }
}