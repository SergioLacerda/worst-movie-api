package com.worst.producer.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SumarizeProducersDTO {
    List<IntervalDTO> min;
    List<IntervalDTO> max;
}
