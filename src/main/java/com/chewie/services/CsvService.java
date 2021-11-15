package com.chewie.services;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.List;



public class CsvService<cl> {

    public Class<cl> clazz;

    CsvService(Class<cl> classGiven) {
        clazz = classGiven;
    }

    public void writeToCSV(@NonNull String filePath, @NonNull List<cl> listToWrite) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Writer writer = new FileWriter(filePath);
        StatefulBeanToCsv<cl> statefulBeanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();
        statefulBeanToCsv.write(listToWrite);
        writer.close();
    }

    public List<cl> readFromCSV(@NonNull String filePath) throws FileNotFoundException {
        FileReader reader = new FileReader(filePath);
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(clazz);
        CsvToBean<cl> csvToBean = new CsvToBeanBuilder<cl>(reader)
                .withSeparator(',')
                .withMappingStrategy(ms)
                .withType(clazz)
                .build();

        return csvToBean.parse();
    }
}
