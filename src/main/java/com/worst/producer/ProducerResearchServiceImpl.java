package com.worst.producer;

import com.worst.producer.domain.MovieEntity;
import com.worst.producer.domain.ProducerPrizesEntity;
import com.worst.producer.domain.dto.ProducerPrizesDTO;
import com.worst.producer.domain.dto.SumarizeProducersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerResearchServiceImpl {

    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private ProducerServiceImpl producerService;

    @Autowired
    private ProducerIntervalServiceImpl producerIntervalService;

    public SumarizeProducersDTO getProducersIntervals() {

        List<MovieEntity> movies = movieService.findMovies();

        List<ProducerPrizesEntity> producerPrizes = producerService.loadProducersPrizesFromMovies(movies);

        List<ProducerPrizesDTO> producerPrizesIntervals = producerIntervalService.calculateProducersIntervals(producerPrizes);

        return producerIntervalService.sumarizeProducerIntervals(producerPrizesIntervals);
    }

}
