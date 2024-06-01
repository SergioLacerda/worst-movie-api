package com.worst.producer.service;

import com.worst.producer.domain.ProducerEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Log4j2
public class InitializeServiceImpl {

    @Autowired
    private ProducerServiceImpl producerService;

    public void initialize(){
        log.atInfo().log("Initialize start");

        List<ProducerEntity> producerEntities = producerService.loadProducersFromCSV();

        Integer totalEntities = isEmpty(producerEntities) ? 0 : producerEntities.size();
        log.atInfo().log(totalEntities + " loaded entities");

        producerService.saveAll(producerEntities);
        log.atInfo().log(totalEntities + " persisted entities");

        log.atInfo().log("Initialize finish");
    }
}
