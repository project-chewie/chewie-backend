package com.chewie.services;

import com.chewie.domain.Saldo;
import com.chewie.domain.User;
import com.chewie.exceptions.SaldoNotFoundException;
import com.chewie.exceptions.UserNotFoundException;
import com.chewie.repositories.SaldoRepository;
import com.chewie.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentest4j.AssertionFailedError;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SaldoServiceTest {

    @InjectMocks
    SaldoService saldoService;

    @Mock
    SaldoRepository saldoRepository;



    @Test
    public void updateSaldoWithNulls(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            saldoService.updateSaldo(null,null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            saldoService.updateSaldo(1L ,null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            saldoService.updateSaldo(null,1L);
        });

}

    @Test
    public void searchSaldoByExistentId(){
        when(saldoRepository.findById(1L)).thenReturn(Optional.of(new Saldo(1L).withValue(0L)));
        assertNotNull(saldoService.findSaldoById(1L));
    }

    @Test
    public void searchSaldoByNotExistentId(){
        assertThrows(SaldoNotFoundException.class,()->{
            assertNull(saldoService.findSaldoById(1L));
        });
    }

    @Test
    public void updateSaldoNotExistentId(){
        assertThrows(NoSuchElementException.class,()->{
            assertNull(saldoService.updateSaldo(1L, 0L));
        });
    }

    @Test
    public void searchAllSaldoNonExistentSaldo(){
        assertThrows(AssertionFailedError.class,()->{
            assertNull(saldoService.findAllSaldos());
        });
    }

    @Test
    public void searchAllSaldoExistentSaldo(){
        when(saldoRepository.findById(1L)).thenReturn(Optional.of(new Saldo(1L).withValue(0L)));
        assertNotNull(saldoService.findAllSaldos());
    }

}
