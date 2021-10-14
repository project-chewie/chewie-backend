package com.chewie.repositories;

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
public class UsersRepositoryTest {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  TestEntityManager em;

    private Long lastInsertId;

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

        var userFound = userRepository.findById(lastInsertId);
        var condition = new Condition<User>(u -> u.isActive(), "user is active per default");
        assertThat(userFound).isPresent();
        assertThat(userFound).hasValueSatisfying(condition);
    }

    @AfterEach
    void after(){
        userRepository.deleteById(lastInsertId);
        lastInsertId=1L;
    }
}