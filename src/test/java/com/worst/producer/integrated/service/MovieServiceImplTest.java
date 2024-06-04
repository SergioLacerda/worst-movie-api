package com.worst.producer.integrated.service;

import com.worst.producer.domain.MovieEntity;
import com.worst.producer.repository.MovieRepository;
import com.worst.producer.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.util.CollectionUtils.isEmpty;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MovieServiceImplTest {

    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void testPersistance() {
        List<MovieEntity> moviesEntities = movieRepository.findAll();
        assertFalse(isEmpty(moviesEntities));
        assertEquals(206 ,moviesEntities.size());
        movieService.save(MovieEntity.builder().title("Test").build());

        List<MovieEntity> moviesEntitiesAfter = movieRepository.findAll();
        assertFalse(isEmpty(moviesEntitiesAfter));
        assertEquals(207 ,moviesEntitiesAfter.size());
    }
}
