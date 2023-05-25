package com.aueb.issues.repository.service;

import com.aueb.issues.repository.representations.UserRepresentation;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.aueb.issues.model.entity.Role.TEACHER;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = ServiceApplication.class
)
public class CsvParserTest {
    @Autowired
    private CsvParser csvParser;
    @Test
    public void parseTest() throws MessagingException, IOException {
        //fill it with data
        UserRepresentation ans= new UserRepresentation("Monika","Coping","mcoping0@livejournal.com","109-883-9639","PO Box 77376", "Female", TEACHER.name(),null);

        MultipartFile result=toMultipart();
        List<UserRepresentation>
        rep=csvParser.readUserCsv(result);
        Assert.assertNotNull(rep);
        Assert.assertEquals(rep.get(0).getFName(), ans.getFName());
    }

    public MultipartFile toMultipart(){
        Path path = Paths.get("C:\\Users\\Admin\\Desktop\\Users.csv");
        String name = "Users.csv";
        String originalFileName = "Users.csv";
        String contentType = "text/csv";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e){}
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        return result;
    }
}