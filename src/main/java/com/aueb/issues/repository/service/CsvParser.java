package com.aueb.issues.repository.service;

import com.aueb.issues.repository.representations.UserRepresentation;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CsvParser {
    public List<UserRepresentation> readUserCsv(MultipartFile file) throws MessagingException, IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            // create csv bean reader
            CsvToBean<UserRepresentation> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(UserRepresentation.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // convert `CsvToBean` object to list of users
            List<UserRepresentation> users = csvToBean.parse();
            return users;
        }
    }
}
