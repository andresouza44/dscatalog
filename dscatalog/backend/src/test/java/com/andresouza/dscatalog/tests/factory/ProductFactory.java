package com.andresouza.dscatalog.tests.factory;

import com.andresouza.dscatalog.entities.Product;

import java.time.Instant;

public class ProductFactory {

    public static Product createProduct(){
        Product product = new Product(1L,"Celular S10","Celular 5G, memória de 128giga",1250.0, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg", Instant.parse( "2020-07-14T10:00:00Z"));
        return  product;
    }
}
