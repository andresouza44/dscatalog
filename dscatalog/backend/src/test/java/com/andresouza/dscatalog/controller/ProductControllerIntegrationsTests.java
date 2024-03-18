package com.andresouza.dscatalog.controller;

import com.andresouza.dscatalog.dto.ProductDTO;
import com.andresouza.dscatalog.servicies.ProductService;
import com.andresouza.dscatalog.tests.factory.Factory;
import com.andresouza.dscatalog.tests.factory.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIntegrationsTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    private Long existId;
    private Long nonExistId;
    private Long countTotalProducts;

    private String username, password, bearerToken;

    @BeforeEach
    void setUp() throws Exception {
        existId = 1L;
        nonExistId = 1000L;
        countTotalProducts = 25L;

        username = "maria@gmail.com";
        password = "123456";

        bearerToken = tokenUtil.obtainAccessToken(mockMvc, username, password);

    }

    @Test
  //  @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void updateShouldReturnNotFoundWhenIdDoesNotExist () throws Exception{
        ProductDTO productDTO = Factory.createProductDTO();
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", nonExistId)
                    .header("Authorization", "Bearer " + bearerToken)
                    .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
    }

    @Test
   // @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateShouldReturnProductDtoWhenIdExist () throws Exception{

        ProductDTO productDTO = Factory.createProductDTO();
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        String expectedName = productDTO.getName();
        String expectedDescription = productDTO.getDescription();

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", existId)
                .header("Authorization", "Bearer " + bearerToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existId));
        result.andExpect(jsonPath("$.name").value(expectedName));
        result.andExpect(jsonPath("$.description").value(expectedDescription));


    }

    @Test
    public void findAllShouldReturnPagedWhenSortByName() throws Exception{
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/products?page=0&size=12&sort=name,asc")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content.[0].name").value("Macbook Pro"));
        result.andExpect(jsonPath("$.content.[1].name").value("PC Gamer"));
        result.andExpect(jsonPath("$.content.[2].name").value("PC Gamer Alfa"));

    }
}

