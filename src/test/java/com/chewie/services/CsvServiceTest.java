package com.chewie.services;

import com.chewie.domain.User;
import com.chewie.repositories.UserRepository;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class CsvServiceTest {

    @Mock
    UserRepository userRepository;

    CsvService<User> userCsvService = new CsvService(User.class);

    @Test
    void writeUserToCSV() {
        List<User> outputFindAll = new ArrayList<User>();
        outputFindAll.add(new User().withUserName("Bob").withPassword("123"));
        outputFindAll.add(new User().withUserName("Karina").withPassword("qwert"));
        when(userRepository.findAll()).thenReturn(outputFindAll);
        try {
            userCsvService.writeToCSV("test2.csv", StreamSupport.stream(userRepository.findAll().spliterator(), false)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readUserFromCSV() {
        try {
            assertNotNull(userCsvService.readFromCSV("test2.csv"));
            System.out.println(userCsvService.readFromCSV("test2.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}