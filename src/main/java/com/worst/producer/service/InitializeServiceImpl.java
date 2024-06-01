package com.worst.producer.service;

import com.worst.producer.domain.MovieEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Log4j2
public class InitializeServiceImpl {

    @Autowired
    private MovieServiceImpl movieService;

    public void initialize(){
        log.atInfo().log("Initialize start");

        List<MovieEntity> movieEntities = movieService.loadMoviesFromCSV();

        Integer totalEntities = isEmpty(movieEntities) ? 0 : movieEntities.size();
        log.atInfo().log(totalEntities + " loaded entities");

        movieService.saveAll(movieEntities);
        log.atInfo().log(totalEntities + " persisted entities");

        log.atInfo().log("Initialize finish");
    }
}
