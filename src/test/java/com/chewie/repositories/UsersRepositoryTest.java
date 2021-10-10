package com.chewie.repositories;
import static org.assertj.core.api.Assertions.assertThat;

import com.chewie.domain.Booking;
import com.chewie.domain.User;
import com.chewie.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.transaction.TestTransaction;

import javax.transaction.Transactional;


@DataJpaTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DirtiesContext
public class UsersRepositoryTest {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  TestEntityManager em;

    private Integer lastInsertId;

    @BeforeEach
    void before() {
        var user = new User();
        user.setName("User");
        user.setPassword("Password");
        lastInsertId=userRepository.save(user).getId();
    }

    @Test
    @DisplayName("find user by id")
    void testFindById() {

        var userFound = userRepository.findById(1);
        var condition = new Condition<User>(u -> u.isActive(), "user is active per default");
        assertThat(userFound).isPresent();
        assertThat(userFound).hasValueSatisfying(condition);
    }

    @AfterEach
    void after(){
        userRepository.deleteAll();
        lastInsertId=0;
    }
}