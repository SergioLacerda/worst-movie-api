package com.worst.producer;

import com.worst.producer.domain.MovieEntity;
import com.worst.producer.repository.MovieRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasLength;

@Service
@Log4j2
public class MovieServiceImpl {

    private static final String CHAR_DELIMITER = ";";
    private static final String CSV_TRUE_VALUE = "YES";

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private FilesServiceImpl filesService;

    public List<MovieEntity> loadMoviesFromCSV(){
        try{
            List<String> rawFiles = filesService.loadFromDefaultPath();

            removeHeader(rawFiles);

            return loadMoviesFromCSV(rawFiles);
        }catch (Exception exception){
            log.atInfo().log("Failed to convert CSV rows into entity.");
            log.atError().log(exception.getMessage());

            return new ArrayList<>();
        }
    }

    public void save(MovieEntity movie){
        try{
            movieRepository.save(movie);
        }catch (Exception exception){
            log.atInfo().log("Failed to persist entity.");
            log.atError().log(exception.getMessage());
        }
    }

    public void saveAll(List<MovieEntity> movieEntities) {
        try{
            movieRepository.saveAll(movieEntities);
        }catch (Exception exception){
            log.atInfo().log("Failed to persist all entities.");
            log.atError().log(exception.getMessage());
        }
    }

    private void removeHeader(List<String> rawFiles) {
        if(isEmpty(rawFiles)){
            return;
        }

        rawFiles.remove(0);
    }

    private List<MovieEntity> loadMoviesFromCSV(List<String> rawLines) {
        List<MovieEntity> result = new ArrayList<>();

        if(isEmpty(rawLines)){
            return result;
        }

        for(String row : rawLines){
            if(hasLength(row)){
                result.add(parseEntity(row));
            }
        }

        return result;
    }

    private MovieEntity parseEntity(String row) {
        try {
            String[] rawEntity = row.split(CHAR_DELIMITER);

            String rawYear = getField(0, rawEntity);
            String rawTitle = getField(1, rawEntity);
            String rawStudio = getField(2, rawEntity);
            String rawProducer = getField(3, rawEntity);
            String rawWinner = getField(4, rawEntity);

            return MovieEntity.builder()
               .year(Integer.parseInt(rawYear))
               .title(rawTitle)
               .studios(rawStudio)
               .producer(rawProducer)
               .winner(rawWinner != null && CSV_TRUE_VALUE.equalsIgnoreCase(rawWinner))
               .build();
        }catch (Exception exception){
            log.atInfo().log("Failed to parse line: ", row);
            log.atError().log(exception.getMessage());

            return MovieEntity.builder().build();
        }
    }

    private static String getField(Integer position, String[] rawEntity) {
        return rawEntity.length > position ? rawEntity[position] : null;
    }

    public List<MovieEntity> findMovies(String producerName) {
        return hasLength(producerName) ? movieRepository.findByProducer(producerName) : movieRepository.findAll();
    }
}
