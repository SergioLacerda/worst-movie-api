package com.worst.producer.service.unit;

import com.worst.producer.domain.ProducerEntity;
import com.worst.producer.domain.dto.IntervalDTO;
import com.worst.producer.domain.dto.ProducerPrizesDTO;
import com.worst.producer.domain.dto.SumarizeProducersDTO;
import com.worst.producer.service.ProducerIntervalServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@SpringBootTest
class ProducerIntervalServiceImplUnitTests {

    private static final String EXPECTED_FIRST_PRODUCER_NAME = "One";
    private static final String EXPECTED_SECOND_PRODUCER_NAME = "Two";

    @Autowired
    private ProducerIntervalServiceImpl producerIntervalService;

    @Test
    void testCalculateIntervalsErrorUnexpectedError() {
        List<ProducerPrizesDTO> response = producerIntervalService.calculateProducersIntervals(loadInvalidData());
        assertNotNull(response);
        assertTrue(isEmpty(response));
    }

    @Test
    void testCalculateIntervals() {
        List<ProducerPrizesDTO> response = producerIntervalService.calculateProducersIntervals(loadData());
        assertNotNull(response);
        assertFalse(isEmpty(response));

        ProducerPrizesDTO firstProducer = response.get(0);
        assertEquals(EXPECTED_FIRST_PRODUCER_NAME, firstProducer.getProducerName());
        assertEquals(1, firstProducer.getIntervals().size());
        assertInterval(EXPECTED_FIRST_PRODUCER_NAME, firstProducer.getIntervals().get(0), 1, 2000, 2001);

        ProducerPrizesDTO secondProducer = response.get(1);

        assertEquals(EXPECTED_SECOND_PRODUCER_NAME, secondProducer.getProducerName());
        assertEquals(2, secondProducer.getIntervals().size());
        assertInterval(EXPECTED_SECOND_PRODUCER_NAME, secondProducer.getIntervals().get(0), 10, 2000, 2010);
        assertInterval(EXPECTED_SECOND_PRODUCER_NAME, secondProducer.getIntervals().get(1), 89, 2010, 2099);
    }

    @Test
    void testSumarizeIntervals() {
        List<ProducerPrizesDTO> input = producerIntervalService.calculateProducersIntervals(loadData());

        SumarizeProducersDTO response = producerIntervalService.sumarizeProducerIntervals(input);
        assertNotNull(response);
        assertFalse(isEmpty(response.getMin()));
        assertEquals(2, response.getMin().size());

        assertInterval(EXPECTED_FIRST_PRODUCER_NAME, response.getMin().get(0), 1, 2000, 2001);
        assertInterval(EXPECTED_SECOND_PRODUCER_NAME,  response.getMin().get(1), 10, 2000, 2010);

        assertFalse(isEmpty(response.getMax()));
        assertEquals(2, response.getMax().size());
        assertInterval(EXPECTED_FIRST_PRODUCER_NAME, response.getMax().get(0), 1, 2000, 2001);
        assertInterval(EXPECTED_SECOND_PRODUCER_NAME, response.getMax().get(1), 89, 2010, 2099);
    }

    private List<ProducerEntity> loadInvalidData() {
        List<ProducerEntity> expectedProducers = new ArrayList<>();
        expectedProducers.add(ProducerEntity.builder().build());

        return expectedProducers;
    }

    private List<ProducerEntity> loadData() {
        List<ProducerEntity> expectedProducers = new ArrayList<>();
        expectedProducers.add(addProducer(EXPECTED_FIRST_PRODUCER_NAME, Arrays.asList(2000, 2001)));
        expectedProducers.add(addProducer(EXPECTED_SECOND_PRODUCER_NAME, Arrays.asList(2000, 2010, 2099)));

        return expectedProducers;
    }

    private static void assertInterval(String producerName, IntervalDTO intervalDTO, int interval, int previous, int next) {
        assertEquals(producerName, intervalDTO.getProducer());
        assertEquals(interval, intervalDTO.getInterval());
        assertEquals(previous, intervalDTO.getPreviousWin());
        assertEquals(next, intervalDTO.getFollowingWin());
    }

    private ProducerEntity addProducer(String name, List<Integer> years) {
        return ProducerEntity.builder()
            .producerName(name)
            .yearWinnerPrizes(years)
            .build();
    }

}
