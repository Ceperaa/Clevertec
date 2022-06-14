package ru.clevertec.check.runner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.check.runner.dto.CheckDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CheckRunnerControllerTest implements ControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @Autowired
    CheckRunnerControllerTest(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void runner() throws Exception {
        mvc.perform(get("/check/runner")
                .param("itemIdQuantity", "1-1")
                .param("idCard", "1")
                .content(objectMapper.writeValueAsString(new CheckDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}