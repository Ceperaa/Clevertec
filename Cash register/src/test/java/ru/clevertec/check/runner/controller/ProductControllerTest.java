package ru.clevertec.check.runner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.clevertec.check.runner.dto.ProductDtoForCreate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest implements ControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @Autowired
    ProductControllerTest(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @Disabled
    void add() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/product/add")
                .content(objectMapper.writeValueAsString(ProductDtoForCreate
                        .builder()
                        //.id(1L)
                        .amount("1")
                        .name("Apple")
                        .discountPercent(1)
                        .price("10.00")
                        .build()))
                .contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/product/1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    void all() throws Exception {
        mvc.perform(get("/product/all"))
                .andExpect(status().isOk());
    }
}