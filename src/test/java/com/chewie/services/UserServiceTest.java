package com.chewie.services;

import com.chewie.domain.User;
import com.chewie.exceptions.UserNotFoundException;
import com.chewie.repositories.UserRepository;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void findCSVFile() {
        //before
        String fileName = "test.csv";
        //File testFile = new File(fileName);
        List<User> outputFindAll = new ArrayList<User>();
        outputFindAll.add(new User().withUserName("Bob").withPassword("123"));
        outputFindAll.add(new User().withUserName("Karina").withPassword("qwert"));
        //while
        when(usersRepository.findAll()).thenReturn(outputFindAll);
        try {
            userService.writeUsersToCSV(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
        try {
            assertNotNull(userService.readUsersFromCSV(fileName));
            System.out.println(userService.readUsersFromCSV(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //after
        //assertTrue(testFile.delete());

    }
}
