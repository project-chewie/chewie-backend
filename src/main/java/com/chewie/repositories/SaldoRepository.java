package com.chewie.repositories;

import com.chewie.domain.Saldo;
import com.chewie.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

    public interface SaldoRepository extends CrudRepository<Saldo, Long>{
        /**
         * Save a given Saldo
         *
         * @param saldo must not be null
         * @return the saved saldo
         */
        Saldo save(Saldo saldo);

        /**
         * Retrieves a Saldo by its id
         *
         * @param id must not be null
         * @return the Saldo with the given id or empty
         */
        Optional<Saldo> findById(Long id);
    }

