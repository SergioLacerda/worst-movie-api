package com.worst.producer.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProducerEntity {
    private String producerName;
    private List<Integer> yearWinnerPrizes;
}
