package com.worst.producer.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProducerPrizesDTO {
    private String producerName;
    private List<IntervalDTO> intervals;
}

