package com.worst.producer.service;

import com.worst.producer.domain.ProducerEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProducerServiceImplUnitTest {

    @InjectMocks
    private ProducerServiceImpl producerService;

    @Mock
    private FilesServiceImpl fileService;

    @Test
    public void testReadFileErrorFileNotFound() {
        prepareMockReadFile(null);

        List<ProducerEntity> response = producerService.loadProducers();
        assertNotNull(response);
        assertTrue(CollectionUtils.isEmpty(response));

        verify(fileService, times(1)).loadFromDefaultPath();
    }

    @Test
    public void testReadFileErrorEmptyFile() {
        prepareMockReadFile(new ArrayList<>());

        List<ProducerEntity> response = producerService.loadProducers();
        assertNotNull(response);
        assertTrue(CollectionUtils.isEmpty(response));

        verify(fileService, times(1)).loadFromDefaultPath();
    }

    @Test
    public void testReadFileErrorInvalidContent() {
        prepareMockReadFile(generateInvalidLines());

        List<ProducerEntity> response = producerService.loadProducers();
        assertNotNull(response);
        assertTrue(CollectionUtils.isEmpty(response));

        verify(fileService, times(1)).loadFromDefaultPath();
    }

    @Test
    public void testReadFile() {
        prepareMockReadFile(generateLines());

        List<ProducerEntity> response = producerService.loadProducers();
        assertNotNull(response);
        assertEquals(1, response.size());

        ProducerEntity producerResponse = response.get(0);
        assertEquals("Producer 1", producerResponse.getProducerName());

        assertNotNull(producerResponse.getYearWinnerPrizes());
        assertEquals(3, producerResponse.getYearWinnerPrizes().size());
        assertEquals(2000, producerResponse.getYearWinnerPrizes().get(0));
        assertEquals(2010, producerResponse.getYearWinnerPrizes().get(1));
        assertEquals(2011, producerResponse.getYearWinnerPrizes().get(2));

        verify(fileService, times(1)).loadFromDefaultPath();
    }

    @Test
    public void testReadFileWithTwoProducers() {
        prepareMockReadFile(generateTwoProducersLines());

        List<ProducerEntity> response = producerService.loadProducers();
        assertNotNull(response);
        assertEquals(2, response.size());

        ProducerEntity producerResponse = response.get(0);
        assertEquals("Producer 1", producerResponse.getProducerName());

        assertNotNull(producerResponse.getYearWinnerPrizes());
        assertEquals(1, producerResponse.getYearWinnerPrizes().size());
        assertEquals(2000, producerResponse.getYearWinnerPrizes().get(0));

        ProducerEntity producerResponse2 = response.get(1);
        assertEquals("Producer 2", producerResponse2.getProducerName());

        assertNotNull(producerResponse2.getYearWinnerPrizes());
        assertEquals(1, producerResponse2.getYearWinnerPrizes().size());
        assertEquals(2010, producerResponse2.getYearWinnerPrizes().get(0));

        verify(fileService, times(1)).loadFromDefaultPath();
    }

    private List<String> generateTwoProducersLines() {
        List<String> result = new ArrayList<>();
        result.add("producer;year;movie-name;");
        result.add("Producer 1;2000;");
        result.add("Producer 2;2010;");

        return result;
    }

    private List<String> generateLines() {
        List<String> result = new ArrayList<>();
        result.add("producer;year;movie-name;");
        result.add("Producer 1;2000;");
        result.add("Producer 1;2010;");
        result.add("Producer 1;2011;");

        return result;
    }

    private List<String> generateInvalidLines() {
        List<String> result = new ArrayList<>();
        result.add("Producer 1;;");
        result.add("Producer 1;tralala; 2000;");
        result.add("abc;");

        return result;
    }

    private void prepareMockReadFile(List<String> expectedResponse) {
        when(fileService.loadFromDefaultPath()).thenReturn(expectedResponse);
    }
}
