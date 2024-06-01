package com.worst.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProducerInitializeData implements CommandLineRunner {

    @Autowired
    private InitializeServiceImpl initializeService;

    @Override
    public void run(String... args) throws Exception {
        initializeService.initialize();
    }
}
