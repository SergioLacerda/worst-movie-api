package com.worst.producer.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntervalDTO {
    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}
