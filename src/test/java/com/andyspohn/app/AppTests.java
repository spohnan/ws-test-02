package com.andyspohn.app;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class AppTests {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new App()).build();
    }

    @Test
    public void testEnv() throws Exception {
        // TODO: Just test that there is somthing returned, not sure how to set an environment var at the moment
        mockMvc.perform(get("/env/USER").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void testBadHealth() throws Exception {
        mockMvc.perform(get("/badhealth").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is5xxServerError());
    }

    @Test @Ignore("Purposefully not testing so we can demonstrate run time failure of health check")
    public void testHealth() throws Exception {
        mockMvc.perform(get("/health").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{'health':'OK'}"));
    }

    @Test
    public void testHostname() throws Exception {
        mockMvc.perform(get("/host").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void testSleep() throws Exception {
        mockMvc.perform(get("/sleep/1").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{'sleep':'1'}"));
    }

    @Test
    public void testVersion() throws Exception {
        mockMvc.perform(get("/version").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }
}
