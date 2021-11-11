package com.chewie.services;

import com.chewie.domain.User;
import com.chewie.exceptions.UserNotFoundException;
import com.chewie.repositories.UserRepository;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    private Object User;

    public User addUser(@NonNull String username, @NonNull String password){
        User newUser = new User();
        newUser.setName(username);
        newUser.setPassword(password);
        return userRepository.save(newUser);
    }

    public User findUserById(@NonNull Long user_id) {
        return userRepository.findById(user_id).orElseThrow(() -> new UserNotFoundException(user_id));
    }

    public List<User> findAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void writeUsersToCSV(@NonNull String filePath) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Writer writer = new FileWriter(filePath);
        StatefulBeanToCsv<User> statefulBeanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();
        List<User> allUsers = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        statefulBeanToCsv.write(allUsers);
        writer.close();
    }

    public List<User> readUsersFromCSV(String filename) throws FileNotFoundException {
        FileReader reader = new FileReader(filename);
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(User.class);
        CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                .withSeparator(',')
                .withMappingStrategy(ms)
                .withType(User.class)
                .build();

        return csvToBean.parse();
    }
}
