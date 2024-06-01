package com.worst.producer.service.unit;

import com.worst.producer.domain.MovieEntity;
import com.worst.producer.repository.MovieRepository;
import com.worst.producer.service.FilesServiceImpl;
import com.worst.producer.service.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@SpringBootTest
class MovieServiceImplUnitTest {
    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private FilesServiceImpl filesService;

    @Test
    void testSave() {
        movieService.save(MovieEntity.builder().build());

        verify(movieRepository, times(1)).save(any(MovieEntity.class));
    }

    @Test
    void testSaveAll() {
        List<MovieEntity> result = new ArrayList<>();
        result.add(MovieEntity.builder().title("test").build());

        movieService.saveAll(result);

        verify(movieRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testLoadMoviesWithError() {
        prepareReadFileError();

        List<MovieEntity> response = movieService.loadMoviesFromCSV();
        assertNotNull(response);
        assertTrue(isEmpty(response));
        verifyNoInteractions(movieRepository);
    }

    @Test
    void testLoadMovies() {
        prepareReadFile(loadContent());

        List<MovieEntity> response = movieService.loadMoviesFromCSV();
        assertNotNull(response);
        assertFalse(isEmpty(response));
        assertEquals(2, response.size());

        MovieEntity firstMovie = response.get(0);
        assertEquals(1980, firstMovie.getYear());
        assertEquals("Can't Stop the Music", firstMovie.getTitle());
        assertEquals("Associated Film Distribution", firstMovie.getStudios());
        assertEquals("Allan Carr", firstMovie.getProducer());
        assertEquals(TRUE, firstMovie.getWinner());

        MovieEntity secondMovie = response.get(1);
        assertEquals(1981, secondMovie.getYear());
        assertEquals("Tarzan, the Ape Man", secondMovie.getTitle());
        assertEquals("MGM, United Artists", secondMovie.getStudios());
        assertEquals("Paul Aratow", secondMovie.getProducer());
        assertEquals(FALSE, secondMovie.getWinner());

        verifyNoInteractions(movieRepository);
    }

    private List<String> loadContent() {
        List<String> expectedResponse = new ArrayList<>();
        expectedResponse.add("year;title;studios;producers;winner");
        expectedResponse.add("1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes");
        expectedResponse.add("1981;Tarzan, the Ape Man;MGM, United Artists;Paul Aratow;");

        return expectedResponse;
    }

    private void prepareReadFile(List<String> expectedResponse) {
        when(filesService.loadFromDefaultPath()).thenReturn(expectedResponse);
    }

    private void prepareReadFileError() {
        when(filesService.loadFromDefaultPath()).thenThrow(new RuntimeException("Unexpected error!!!"));
    }

}
