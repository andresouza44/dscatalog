package com.andresouza.dscatalog.controller;

import com.andresouza.dscatalog.dto.ProductDTO;
import com.andresouza.dscatalog.servicies.ProductService;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import com.andresouza.dscatalog.tests.factory.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.lang.management.MonitorInfo;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @MockBean
    private ProductService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private ProductDTO productDTO;
    private PageImpl page;
    private Long existId;
    private Long nonExistId;
    private Long indentedId;

    @BeforeEach
    void setUp() {
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(productDTO));
        existId = 1L;
        nonExistId = 2L;
        indentedId = 3L;

        Mockito.when(service.findAll(ArgumentMatchers.any())).thenReturn(page);

        Mockito.when((service.findById(existId))).thenReturn(productDTO);
        Mockito.when(service.findById(nonExistId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when((service.update(eq(existId),ArgumentMatchers.any()))).thenReturn(productDTO);
        Mockito.when(service.update(eq(nonExistId),ArgumentMatchers.any())).thenThrow(ResourceNotFoundException.class);

        Mockito.doNothing().when(service).deletById(existId);
        Mockito.doThrow(ResourceNotFoundException.class).when(service).deletById(nonExistId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(service).deletById(indentedId);

        Mockito.when(service.insert(any())).thenReturn(productDTO);

    }
    @Test
    public void deleteByIdShouldReturnNoContentWhenIdExist () throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/{id}", existId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteByIdShouldReturnNotFoundWhenIdNotExist() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/{id}", nonExistId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void insertShouldReturnProductDtoCreated () throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());


    }


    @Test
    public void updateByIdShouldReturnProductDtoWhenIdExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", existId)
                    .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());


    }

    @Test
    public void updateByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        String jsonbody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/product/{id}", nonExistId)
                .content(jsonbody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }


    @Test
    public void findByIdShouldReturnProductDtoWhenIdExist() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}",existId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());

    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", nonExistId));
        result.andExpect((status().isNotFound()));
    }


    @Test
    public void findAllPagedShouldReturnPage() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/products")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

    }
}
