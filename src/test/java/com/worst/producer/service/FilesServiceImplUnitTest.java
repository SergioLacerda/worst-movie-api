package com.worst.producer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilesServiceImplUnitTest {

    @Autowired
    private FilesServiceImpl fileService;

    @Test
    public void testReadFileErrorInvalidInput() {
        List<String> stringsRead = fileService.readFile(null);
        assertNotNull(stringsRead);
        assertTrue(CollectionUtils.isEmpty(stringsRead));

        List<String> stringsRead2 = fileService.readFile("");
        assertNotNull(stringsRead2);
        assertTrue(CollectionUtils.isEmpty(stringsRead2));
    }

    @Test
    public void testReadFileErrorInvalidContent() {
        String filePath = "src/test/resources/readfile_invalid_test.csv";

        List<String> stringsRead = fileService.readFile(filePath);
        assertNotNull(stringsRead);
        assertTrue(CollectionUtils.isEmpty(stringsRead));
    }

    @Test
    public void testReadFile() {
        String expectedHeader = "producer;year;movie-name;";
        String expectedRow = "Producer 1;2000;tralala;";
        String expectedRow2 = "Producer 1;2001;";

        List<String> stringsRead = fileService.readFile("src/test/resources/readfile_ok_test.csv");
        assertNotNull(stringsRead);

        assertEquals(3, stringsRead.size());
        assertEquals(expectedHeader, stringsRead.get(0));
        assertEquals(expectedRow, stringsRead.get(1));
        assertEquals(expectedRow2, stringsRead.get(2));
    }
}
