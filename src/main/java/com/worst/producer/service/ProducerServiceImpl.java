package com.worst.producer.service;

import com.worst.producer.domain.ProducerEntity;
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

    private static final String CHAR_DELIMITER = ";";

    @Autowired
    private FilesServiceImpl filesService;

    public List<ProducerEntity> loadProducers(){
        try{
            List<String> rawFiles = filesService.loadFromDefaultPath();

            removeHeader(rawFiles);

            return loadProducers(rawFiles);
        }catch (Exception exception){
            log.atInfo().log("Failed to convert CSV rows into entity.");
            log.atError().log(exception.getMessage());

            return new ArrayList<>();
        }
    }

    private void removeHeader(List<String> rawFiles) {
        if(isEmpty(rawFiles)){
            return;
        }

        rawFiles.remove(0);
    }

    private List<ProducerEntity> loadProducers(List<String> rawLines) {
        if(isEmpty(rawLines)){
            return new ArrayList<>();
        }

        Map<String, List<Integer>> mapProducer = new HashMap<>();
        for(String row : rawLines){
            parseEntity(mapProducer, row);
        }

        List<ProducerEntity> producers = new ArrayList<>();
        mapProducer.entrySet().forEach( rawProducer -> producers.add(
            ProducerEntity.builder()
                .producerName(rawProducer.getKey())
                .yearWinnerPrizes(rawProducer.getValue())
                .build())
        );

        return producers;
    }

    private void parseEntity(Map<String, List<Integer>> mapProducer, String row) {
        try {
            String[] rawEntity = row.split(CHAR_DELIMITER);

            String rawProducer = rawEntity[0];
            String rawYear = rawEntity[1];

            mapProducer.put(rawProducer, addYear(mapProducer.get(rawProducer), rawYear));
        }catch (Exception exception){
            log.atInfo().log("Failed to parse line: ", row);
            log.atError().log(exception.getMessage());
        }
    }

    private List<Integer> addYear(List<Integer> rawYears, String rawYear) {
        List<Integer> result = isEmpty(rawYears) ? new ArrayList<>() : rawYears;

        result.add(Integer.valueOf(rawYear));

        return result;
    }
}
