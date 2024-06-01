package com.worst.producer.service.integrated;

import com.worst.producer.domain.MovieEntity;
import com.worst.producer.repository.MovieRepository;
import com.worst.producer.service.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(7 ,moviesEntities.size());
        movieService.save(MovieEntity.builder().title("Test").build());

        List<MovieEntity> moviesEntitiesAfter = movieRepository.findAll();
        assertFalse(isEmpty(moviesEntitiesAfter));
        assertEquals(8 ,moviesEntitiesAfter.size());
    }
}
