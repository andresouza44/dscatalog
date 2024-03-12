package com.andresouza.dscatalog.servicies;

import com.andresouza.dscatalog.dto.ProductDTO;
import com.andresouza.dscatalog.repositories.ProductRepository;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIntegrationsTests {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    private Long existId;
    private Long nonExitId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() {
        existId = 1L;
        nonExitId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    public void findAllPagedShouldReturnSortedPageWhenSortByName(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("name"));
        Page<ProductDTO> result = service.findAll(pageable);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Macbook Pro",result.getContent().get(0).getName());
        Assertions.assertEquals("PC Gamer",result.getContent().get(1).getName());
        Assertions.assertEquals("PC Gamer Alfa",result.getContent().get(2).getName());
    }

    @Test
    public void findAllPageShouldReturnEmptyWhenPageDoesNotExist (){
        Pageable pageable = PageRequest.of(50,10);
        Page<ProductDTO> result = service.findAll(pageable);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findAllPageShouldReturnPageProductDtoWhenPage0Size10 (){
        Pageable pageable = PageRequest.of(0,10);
        Page<ProductDTO> result = service.findAll(pageable);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0,result.getNumber());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());

    }
    @Test
    public void deleteShouldDeleteResourceWhenIdExist (){
        service.deletById(existId);
        Assertions.assertEquals(countTotalProducts -1, repository.count());
    }
    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExits(){
        Assertions.assertThrows(ResourceNotFoundException.class, () ->
                service.deletById(nonExitId));
    }
}


