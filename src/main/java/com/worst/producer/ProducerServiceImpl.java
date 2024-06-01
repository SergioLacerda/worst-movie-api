package com.worst.producer;

import com.worst.producer.domain.MovieEntity;
import com.worst.producer.domain.ProducerPrizesEntity;
import com.worst.producer.repository.ProducerPrizeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Log4j2
public class ProducerServiceImpl {

    @Autowired
    private ProducerPrizeRepository producerPrizeRepository;

    public void save(ProducerPrizesEntity producer){
        try{
            producerPrizeRepository.save(producer);
        }catch (Exception exception){
            log.atInfo().log("Failed to persist entity.");
            log.atError().log(exception.getMessage());
        }
    }

    public void saveAll(List<ProducerPrizesEntity> producerEntities) {
        try{
            producerPrizeRepository.saveAll(producerEntities);
        }catch (Exception exception){
            log.atInfo().log("Failed to persist all entities.");
            log.atError().log(exception.getMessage());
        }
    }

    public List<ProducerPrizesEntity> loadProducersPrizesFromMovies(List<MovieEntity> movies) {
        if(isEmpty(movies)){
            return new ArrayList<>();
        }

        Map<String, List<Integer>> mapProducer = new HashMap<>();
        for(MovieEntity movie : movies){
            parseProducer(mapProducer, movie);
        }

        List<ProducerPrizesEntity> producers = new ArrayList<>();
        mapProducer.entrySet().forEach( rawProducer -> producers.add(
            ProducerPrizesEntity.builder()
                .producerName(rawProducer.getKey())
                .yearWinnerPrizes(rawProducer.getValue())
                .build())
        );

        return producers;
    }

    private void parseProducer(Map<String, List<Integer>> mapProducer, MovieEntity movie) {
        try {

            String producerName = movie.getProducer();
            mapProducer.put(producerName, addYear(mapProducer.get(producerName), movie.getYear()));
        }catch (Exception exception){
            log.atInfo().log("Failed to parse from movie: ", movie);
            log.atError().log(exception.getMessage());
        }
    }

    private List<Integer> addYear(List<Integer> rawYears, Integer year) {
        List<Integer> result = isEmpty(rawYears) ? new ArrayList<>() : rawYears;

        result.add(year);

        return result;
    }

}
