package com.chewie.domain;


import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Saldo {
        private static final long serialVersionUID = 1L;
        @Id
        private Long id;
        private Long value;

        public Saldo() {
                this.setId(0L);
                this.setValue(0L);
        }

        public Saldo(@NonNull Long user_id, @NonNull Long saldo_value){
                this.setId(user_id);
                this.setValue(saldo_value);
        }

        public Saldo(@NonNull Long user_id){
                this.setId(user_id);
                this.setValue(0L);
        }

        public Saldo withValue(@NonNull Long value) {
                this.setValue(value);
                return this;
        }
}
