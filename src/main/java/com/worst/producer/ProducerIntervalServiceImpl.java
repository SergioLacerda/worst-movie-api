package com.worst.producer;

import com.worst.producer.domain.ProducerPrizesEntity;
import com.worst.producer.domain.dto.IntervalDTO;
import com.worst.producer.domain.dto.ProducerPrizesDTO;
import com.worst.producer.domain.dto.SumarizeProducersDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Log4j2
public class ProducerIntervalServiceImpl {

    public List<ProducerPrizesDTO> calculateProducersIntervals(List<ProducerPrizesEntity> producers){
        try{
            List<ProducerPrizesDTO> result = new ArrayList<>();
            for(ProducerPrizesEntity producer : producers){
                result.add(ProducerPrizesDTO.builder()
                    .producerName(producer.getProducerName())
                    .intervals(calculateIntervals(producer.getProducerName(), producer.getYearWinnerPrizes()))
                    .build());
            }

            return result;
        }catch (Exception exception){
            log.atInfo().log("Failed to calculate intervals between producer prizes.");
            log.atError().log(exception.getMessage());

            return new ArrayList<>();
        }
    }

    public SumarizeProducersDTO sumarizeProducerIntervals(List<ProducerPrizesDTO> producersIntervals){
        List<IntervalDTO> min = new ArrayList<>();
        List<IntervalDTO> max = new ArrayList<>();

        try{
            for(ProducerPrizesDTO producer : producersIntervals){
                producer.getIntervals()
                        .stream()
                        .min(Comparator.comparingInt(IntervalDTO::getInterval))
                        .ifPresent(min::add);

                producer.getIntervals()
                        .stream()
                        .max(Comparator.comparingInt(IntervalDTO::getInterval))
                        .ifPresent(max::add);
            }
        }catch (Exception exception){
            log.atInfo().log("Failed to sumarize producer intervals.");
            log.atError().log(exception.getMessage());
        }

        return SumarizeProducersDTO.builder().min(min).max(max).build();
    }

    private List<IntervalDTO> calculateIntervals(String producerName, List<Integer> list) {
        List<IntervalDTO> result = new ArrayList<>();

        for (int position = 1; position < list.size(); position++) {
            Integer followingWin = list.get(position);
            Integer previousWin = list.get(position - 1);

            int interval = followingWin - previousWin;

            result.add(IntervalDTO.builder()
                .producer(producerName)
                .interval(interval)
                .previousWin(previousWin)
                .followingWin(followingWin)
                .build());
        }

        return result;
    }
}
