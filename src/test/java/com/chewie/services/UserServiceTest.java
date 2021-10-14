package com.chewie.services;

import com.chewie.domain.User;
import com.chewie.exceptions.UserNotFoundException;
import com.chewie.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository usersRepository;



    @Test
    public void addUserWithNulls(){

        Assertions.assertThrows(NullPointerException.class, () -> {
            userService.addUser(null,null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            userService.addUser("aName",null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            userService.addUser(null,"aPassword");
        });

    }

    @Test
    public void searchUserByExistentId(){
        when(usersRepository.findById(1L)).thenReturn(Optional.of(new User().withUserName("Username").withPassword("Password")));
        assertNotNull(userService.findUserById(1L));
    }

    @Test
    public void searchUserByNotExistentId(){
        assertThrows(UserNotFoundException.class,()->{
            assertNull(userService.findUserById(1L));
        });

    }
}
