package com.chewie.repositories;

import com.chewie.domain.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations="classpath:mysql-test.properties")
public class UsersRepositoryMySqlContainerTest {


    @Container
    private static MySQLContainer database = new MySQLContainer("mysql");

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
        assertThat(userFound).isPresent();
    }

    @AfterEach
    void after(){
        userRepository.deleteById(lastInsertId);
        lastInsertId=1L;
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        registry.add("spring.liquibase.url", database::getJdbcUrl);
        registry.add("spring.liquibase.user", database::getUsername);
        registry.add("spring.liquibase.password", database::getPassword);
    }
}