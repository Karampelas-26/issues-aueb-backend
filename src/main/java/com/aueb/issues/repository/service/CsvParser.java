package com.aueb.issues.repository.service;

import com.aueb.issues.repository.representations.UserRepresentation;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CsvParser {
    public List<UserRepresentation> readUserCsv(Multipart file) throws MessagingException, IOException {

        CSVReader reader= new CSVReader(new InputStreamReader(file.getParent().getInputStream()));
        List<UserRepresentation> babies= new CsvToBeanBuilder(reader)
                .withType(UserRepresentation.class)
                .build().parse();
        reader.close();;
        List<UserRepresentation> ret=new ArrayList<>();
        for (UserRepresentation baby:babies){
            //ret.add(baby);
            log.info(baby.toString());
        }
        return ret;
    }
}
