package com.worst.producer.controller;

import com.worst.producer.domain.dto.SumarizeProducersDTO;
import com.worst.producer.ProducerResearchServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@RestController
@Validated
@AllArgsConstructor
@RequestMapping(path = "/v1/producer")
public class ProducerController {

    @Autowired
    private ProducerResearchServiceImpl producerResearchService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SumarizeProducersDTO> getAccountsParameters(@RequestParam(required = false) String producerName) {
        SumarizeProducersDTO response = producerResearchService.getProducersIntervals(producerName);

        return isInvalid(response) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(response);
    }

    private boolean isInvalid(SumarizeProducersDTO response) {
        return (response == null || isEmpty(response.getMin()) || isEmpty(response.getMax()));
    }

}
