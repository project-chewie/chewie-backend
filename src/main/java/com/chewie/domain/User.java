package com.chewie.domain;

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
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(nullable = false)
    private String password;
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