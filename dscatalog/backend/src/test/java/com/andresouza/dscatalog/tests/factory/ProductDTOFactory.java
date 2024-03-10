package com.andresouza.dscatalog.tests.factory;

import com.andresouza.dscatalog.dto.ProductDTO;
import com.andresouza.dscatalog.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductDTOFactory {

    public ProductDTO createProductDTO(){
        Product product = ProductFactory.createProduct();
        ProductDTO productDTO = new ProductDTO(product);
        return  productDTO;

    }
}
