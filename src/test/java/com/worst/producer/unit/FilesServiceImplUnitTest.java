package com.worst.producer.unit;

import com.worst.producer.FilesServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@SpringBootTest
class FilesServiceImplUnitTest {

    @Autowired
    private FilesServiceImpl fileService;

    @Test
    void testReadFileErrorInvalidInput() {
        List<String> stringsRead = fileService.readFile(null);
        assertNotNull(stringsRead);
        assertTrue(isEmpty(stringsRead));

        List<String> stringsRead2 = fileService.readFile("");
        assertNotNull(stringsRead2);
        assertTrue(isEmpty(stringsRead2));
    }

    @Test
    void testReadFileErrorInvalidContent() {
        String filePath = "classpath:readfile_invalid_test.csv";

        List<String> stringsRead = fileService.readFile(filePath);
        assertNotNull(stringsRead);
        assertTrue(isEmpty(stringsRead));
    }

    @Test
    void testReadFile() {
        String expectedHeader = "year;title;studios;producers;winner";
        String expectedRow = "1980;title;studios;Teste01;yes";
        String expectedRow2 = "1984;title;studios;Teste01;";

        List<String> stringsRead = fileService.readFile("classpath:readfile_ok_test.csv");
        assertNotNull(stringsRead);

        assertEquals(3, stringsRead.size());
        assertEquals(expectedHeader, stringsRead.get(0));
        assertEquals(expectedRow, stringsRead.get(1));
        assertEquals(expectedRow2, stringsRead.get(2));
    }
}
