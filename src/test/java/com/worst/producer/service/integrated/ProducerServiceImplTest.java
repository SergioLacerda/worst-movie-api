package com.worst.producer.service.integrated;

import com.worst.producer.domain.ProducerEntity;
import com.worst.producer.repository.ProducerRepository;
import com.worst.producer.service.ProducerServiceImpl;
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
class ProducerServiceImplTest {

    @Autowired
    private ProducerServiceImpl producerService;

    @Autowired
    private ProducerRepository producerRepository;

    @Test
    void testReadFileErrorInvalidInput() {
        List<ProducerEntity> producerEntities = producerRepository.findAll();
        assertTrue(isEmpty(producerEntities));

        producerService.save(ProducerEntity.builder()
            .producerName("teste")
            .yearWinnerPrizes(Arrays.asList(2000, 2001))
            .build());

        List<ProducerEntity> producerEntitiesAfter = producerRepository.findAll();
        assertFalse(isEmpty(producerEntitiesAfter));
        assertEquals(1 ,producerEntitiesAfter.size());
    }
}
