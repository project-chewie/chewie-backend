package com.chewie.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Saldo {
        private static final long serialVersionUID = 1L;
        @Id
        @GeneratedValue
        private Long id;
        private Long value;
}
