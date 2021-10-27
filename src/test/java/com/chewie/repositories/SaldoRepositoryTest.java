package com.chewie.repositories;

import com.chewie.domain.Saldo;
import com.chewie.domain.User;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;




    @DataJpaTest
    @RequiredArgsConstructor
    @TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
    @DirtiesContext
    public class SaldoRepositoryTest {
        @Autowired
        private  SaldoRepository saldoRepository;
        @Autowired
        private TestEntityManager em;

        private Long lastInsertId;

        @BeforeEach
        void before() {
            var saldo = new Saldo(1L);
            saldo.setValue(0L);
            lastInsertId=saldoRepository.save(saldo).getId();
        }

        @Test
        @DisplayName("find Saldo by id")
        void testFindById() {

            var saldoFound = saldoRepository.findById(lastInsertId);
            assertThat(saldoFound).isPresent();
        }

        @AfterEach
        void after(){
            saldoRepository.deleteById(lastInsertId);
            lastInsertId=1L;
        }
    }

