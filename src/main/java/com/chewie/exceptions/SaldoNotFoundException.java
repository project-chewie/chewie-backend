package com.chewie.exceptions;

public class SaldoNotFoundException extends RuntimeException {

    public SaldoNotFoundException(Long id) {
        super("Could not find Saldo for User with id: " + id);
    }
}