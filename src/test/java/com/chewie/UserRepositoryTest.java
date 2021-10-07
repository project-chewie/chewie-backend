package com.chewie;
import static org.assertj.core.api.Assertions.assertThat;

import com.chewie.domain.User;
import com.chewie.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestConstructor;


@DataJpaTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserRepositoryTest {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  TestEntityManager em;

    @BeforeEach
    void before() {
        var user = new User();
        user.setName("User");
        user.setPassword("Password");
        em.persist(user);
    }

    @Test
    @DisplayName("find user by id")
    void testFindById() {
        var user = userRepository.findById(1);
        var condition = new Condition<User>(h -> h.isActive(), "user is active per default");
        assertThat(user).isPresent();
        assertThat(user).hasValueSatisfying(condition);
    }
}