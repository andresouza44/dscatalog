package com.andresouza.dscatalog.repositories;

import com.andresouza.dscatalog.entities.Product;
import com.andresouza.dscatalog.tests.factory.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    ProductRepository repository;

    private static Long existedId;
    private static Long noExistId;
    private static Integer countTotalProducts;
    private static Product product;

    @BeforeEach
    void setUp() {
        existedId = 1L;
        noExistId = 1000L;
        countTotalProducts = 25;
        product = ProductFactory.createProduct();
    }

    @Test
    public void deleteShouldDeleteWhenIdExist(){
        repository.deleteById(existedId);
        Optional<Product> result = repository.findById(existedId);

        Assertions.assertFalse(result.isPresent());

    }

    @Test
    public void saveShouldPersistWhitAutoIncrementWhenIdIsNull(){
        product.setId(null);
        repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts+1 , product.getId());

    }

    @Test
    public void findByIdShouldReturnNomEmptyOptionalWhenExistId(){
        Optional<Product> optional = repository.findById(existedId);

        Assertions.assertTrue(optional.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenDoesNotExistId(){
        Optional<Product> optional = repository.findById(noExistId);

        Assertions.assertTrue(optional.isEmpty());
    }
}
