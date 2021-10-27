package com.chewie.services;

import com.chewie.domain.Saldo;
import com.chewie.exceptions.SaldoNotFoundException;
import com.chewie.exceptions.UserNotFoundException;
import com.chewie.repositories.SaldoRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;



@Service
public class SaldoService {
    @Autowired
    SaldoRepository saldoRepository;

    public Saldo updateSaldo(@NonNull Long user_id, @NonNull Long saldo_value){
        Optional<Saldo> toBeUpdatedSaldo = saldoRepository.findById(user_id);
        Saldo saldo = toBeUpdatedSaldo.get();
        saldo.setValue(saldo_value);
        return saldoRepository.save(saldo);
    }

    public Saldo findSaldoById(@NonNull Long user_id) {
        return saldoRepository.findById(user_id).orElseThrow(() -> new SaldoNotFoundException(user_id));
    }

    public List<Saldo> findAllSaldos() {
        return StreamSupport.stream(saldoRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
