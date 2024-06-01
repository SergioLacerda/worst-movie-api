package com.worst.producer.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@Validated
@AllArgsConstructor
@RequestMapping(path = "/v1/producer")
public class ProducerController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAccountsParameters(@RequestParam(required = false) String producerName) {

        return ResponseEntity.ok().body(new ArrayList<>());
    }
}
