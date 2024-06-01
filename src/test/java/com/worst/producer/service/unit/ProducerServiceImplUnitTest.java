package com.worst.producer.service.unit;

import com.worst.producer.domain.MovieEntity;
import com.worst.producer.domain.ProducerPrizesEntity;
import com.worst.producer.repository.ProducerPrizeRepository;
import com.worst.producer.service.ProducerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.util.CollectionUtils.isEmpty;

@SpringBootTest
class ProducerServiceImplUnitTest {

    @InjectMocks
    private ProducerServiceImpl producerService;

    @Mock
    private ProducerPrizeRepository producerPrizeRepository;

    @Test
    void testLoadProducersWithoutMovies() {
        List<ProducerPrizesEntity> response = producerService.loadProducersPrizesFromMovies(null);
        assertNotNull(response);
        assertTrue(isEmpty(response));
        verifyNoInteractions(producerPrizeRepository);

        List<ProducerPrizesEntity> response2 = producerService.loadProducersPrizesFromMovies(new ArrayList<>());
        assertNotNull(response2);
        assertTrue(isEmpty(response2));

        verifyNoInteractions(producerPrizeRepository);
    }

    @Test
    void testLoadProducersWithInvalidContent() {
        List<ProducerPrizesEntity> response = producerService.loadProducersPrizesFromMovies(loadInvalidMovies());
        assertNotNull(response);
        assertTrue(isEmpty(response));

        verifyNoInteractions(producerPrizeRepository);
    }

    @Test
    void testLoadProducers() {

        List<ProducerPrizesEntity> response = producerService.loadProducersPrizesFromMovies(loadMovies());
        assertNotNull(response);
        assertEquals(2, response.size());

        ProducerPrizesEntity producerResponse = response.get(0);
        assertEquals("Producer 1", producerResponse.getProducerName());

        assertNotNull(producerResponse.getYearWinnerPrizes());
        assertEquals(1, producerResponse.getYearWinnerPrizes().size());
        assertEquals(2000, producerResponse.getYearWinnerPrizes().get(0));

        ProducerPrizesEntity producerResponse2 = response.get(1);
        assertEquals("Producer 2", producerResponse2.getProducerName());

        assertNotNull(producerResponse2.getYearWinnerPrizes());
        assertEquals(1, producerResponse2.getYearWinnerPrizes().size());
        assertEquals(2010, producerResponse2.getYearWinnerPrizes().get(0));

        verifyNoInteractions(producerPrizeRepository);
    }

    private List<MovieEntity> loadMovies() {
        List<MovieEntity> result = new ArrayList<>();

        result.add(MovieEntity.builder().producer("Producer 1").year(2000).winner(TRUE).build());

        result.add(MovieEntity.builder().producer("Producer 2").year(2010).winner(TRUE).build());

        return result;
    }

    private List<MovieEntity> loadInvalidMovies() {
        List<MovieEntity> result = new ArrayList<>();

        return result;
    }
}
