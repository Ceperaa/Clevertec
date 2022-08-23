package ru.clevertec.check.runner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.clevertec.check.runner.model.DiscountCard;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DiscountCardControllerTest implements ControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @Autowired
    DiscountCardControllerTest(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void add() throws Exception {
        mvc.perform(put("/card/add")
                .content(objectMapper.writeValueAsString(new DiscountCard()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void all() throws Exception {
        mvc.perform(get("/card/all"))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/card/1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }
}