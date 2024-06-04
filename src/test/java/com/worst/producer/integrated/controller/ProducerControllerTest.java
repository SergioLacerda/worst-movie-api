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
        String expectedContent = "{\"min\":[{\"producer\":\"Jason Friedberg, Peter Safran and Aaron Seltzer\",\"interval\":2,\"previousWin\":2008,\"followingWin\":2010},{\"producer\":\"Buzz Feitshans\",\"interval\":3,\"previousWin\":1985,\"followingWin\":1988},{\"producer\":\"Michael Bay and Jerry Bruckheimer\",\"interval\":3,\"previousWin\":1998,\"followingWin\":2001},{\"producer\":\"Wyck Godfrey, Stephenie Meyer and Karen Rosenfelt\",\"interval\":1,\"previousWin\":2011,\"followingWin\":2012},{\"producer\":\"Rick McCallum and George Lucas\",\"interval\":3,\"previousWin\":1999,\"followingWin\":2002},{\"producer\":\"Joel Silver\",\"interval\":2,\"previousWin\":1989,\"followingWin\":1991},{\"producer\":\"Yoram Globus and Menahem Golan\",\"interval\":1,\"previousWin\":1986,\"followingWin\":1987},{\"producer\":\"Dino De Laurentiis\",\"interval\":8,\"previousWin\":1985,\"followingWin\":1993},{\"producer\":\"Kevin Costner, Lawrence Kasdan and Jim Wilson\",\"interval\":2,\"previousWin\":1992,\"followingWin\":1994},{\"producer\":\"Jerry Weintraub\",\"interval\":4,\"previousWin\":1994,\"followingWin\":1998},{\"producer\":\"Bo Derek\",\"interval\":6,\"previousWin\":1984,\"followingWin\":1990},{\"producer\":\"Albert S. Ruddy\",\"interval\":2,\"previousWin\":1982,\"followingWin\":1984},{\"producer\":\"Allan Carr\",\"interval\":4,\"previousWin\":1980,\"followingWin\":1984},{\"producer\":\"Lorenzo di Bonaventura, Ian Bryce, Tom DeSanto and Don Murphy\",\"interval\":2,\"previousWin\":2009,\"followingWin\":2011}],\"max\":[{\"producer\":\"Jason Friedberg, Peter Safran and Aaron Seltzer\",\"interval\":2,\"previousWin\":2008,\"followingWin\":2010},{\"producer\":\"Buzz Feitshans\",\"interval\":3,\"previousWin\":1985,\"followingWin\":1988},{\"producer\":\"Michael Bay and Jerry Bruckheimer\",\"interval\":3,\"previousWin\":1998,\"followingWin\":2001},{\"producer\":\"Wyck Godfrey, Stephenie Meyer and Karen Rosenfelt\",\"interval\":1,\"previousWin\":2011,\"followingWin\":2012},{\"producer\":\"Rick McCallum and George Lucas\",\"interval\":3,\"previousWin\":1999,\"followingWin\":2002},{\"producer\":\"Joel Silver\",\"interval\":2,\"previousWin\":1989,\"followingWin\":1991},{\"producer\":\"Yoram Globus and Menahem Golan\",\"interval\":3,\"previousWin\":1983,\"followingWin\":1986},{\"producer\":\"Dino De Laurentiis\",\"interval\":8,\"previousWin\":1985,\"followingWin\":1993},{\"producer\":\"Kevin Costner, Lawrence Kasdan and Jim Wilson\",\"interval\":2,\"previousWin\":1992,\"followingWin\":1994},{\"producer\":\"Jerry Weintraub\",\"interval\":9,\"previousWin\":1980,\"followingWin\":1989},{\"producer\":\"Bo Derek\",\"interval\":6,\"previousWin\":1984,\"followingWin\":1990},{\"producer\":\"Albert S. Ruddy\",\"interval\":2,\"previousWin\":1982,\"followingWin\":1984},{\"producer\":\"Allan Carr\",\"interval\":4,\"previousWin\":1980,\"followingWin\":1984},{\"producer\":\"Lorenzo di Bonaventura, Ian Bryce, Tom DeSanto and Don Murphy\",\"interval\":2,\"previousWin\":2009,\"followingWin\":2011}]}";

        mockMvc.perform(get("/v1/producer"))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedContent));
    }
}
