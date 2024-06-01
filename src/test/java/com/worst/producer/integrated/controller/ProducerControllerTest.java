package com.worst.producer.integrated.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void researchProducers() throws Exception {
        String expectedContent = "{\"min\":[{\"producer\":\"TEST01\",\"interval\":0,\"previousWin\":1980,\"followingWin\":1980},{\"producer\":\"TEST02\",\"interval\":1,\"previousWin\":2000,\"followingWin\":2001}],\"max\":[{\"producer\":\"TEST01\",\"interval\":1,\"previousWin\":1980,\"followingWin\":1981},{\"producer\":\"TEST02\",\"interval\":98,\"previousWin\":2001,\"followingWin\":2099}]}";

        mockMvc.perform(get("/v1/producer"))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedContent));
    }

    @Test
    void researchProducersWithSpecificProducer() throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("producerName", "TEST01");

        String expectedContent = "{\"min\":[{\"producer\":\"TEST01\",\"interval\":0,\"previousWin\":1980,\"followingWin\":1980}],\"max\":[{\"producer\":\"TEST01\",\"interval\":1,\"previousWin\":1980,\"followingWin\":1981}]}";

        mockMvc.perform(get("/v1/producer").params(requestParams))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedContent));
    }

    @Test
    void researchProducersWithInvalidProducer() throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("producerName", "INVALID");

        mockMvc.perform(get("/v1/producer").params(requestParams))
               .andExpect(status().isNotFound())
               .andExpect(content().string(""));
    }
}
