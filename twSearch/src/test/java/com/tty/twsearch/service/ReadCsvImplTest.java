package com.tty.twsearch.service;

import com.tty.twsearch.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ReadCsvImplTest extends BaseTest {

    @Autowired
    ReadCsv readCsv;

    @Test
    void readCsv() {
        readCsv.readCsv("/Users/tty/git/TwitterKeywordSearch/twSearch/src/main/resources/csv/abcnews-date-text.csv");
    }
}