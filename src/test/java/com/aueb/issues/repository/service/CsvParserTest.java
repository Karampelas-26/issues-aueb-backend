package com.aueb.issues.repository.service;

import jakarta.mail.Multipart;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@RunWith(SpringRunner.class)

class CsvParserTest {
    @Autowired
    CsvParser csvParser;

}