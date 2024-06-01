package com.worst.producer.integrated.service;

import com.worst.producer.domain.ProducerPrizesEntity;
import com.worst.producer.repository.ProducerPrizeRepository;
import com.worst.producer.ProducerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProducerPrizeServiceImplTest {

    @Autowired
    private ProducerServiceImpl producerService;

    @Autowired
    private ProducerPrizeRepository producerPrizeRepository;

    @Test
    void testPersistance() {
        List<ProducerPrizesEntity> producerEntities = producerPrizeRepository.findAll();
        assertTrue(isEmpty(producerEntities));

        producerService.save(ProducerPrizesEntity.builder()
            .producerName("teste")
            .yearWinnerPrizes(Arrays.asList(2000, 2001))
            .build());

        List<ProducerPrizesEntity> producerEntitiesAfter = producerPrizeRepository.findAll();
        assertFalse(isEmpty(producerEntitiesAfter));
        assertEquals(1 ,producerEntitiesAfter.size());
    }
}
