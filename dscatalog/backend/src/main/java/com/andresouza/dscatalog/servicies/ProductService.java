package com.andresouza.dscatalog.servicies;

import com.andresouza.dscatalog.dto.CategoryDTO;
import com.andresouza.dscatalog.dto.ProductDTO;
import com.andresouza.dscatalog.entities.Category;
import com.andresouza.dscatalog.entities.Product;
import com.andresouza.dscatalog.repositories.ProductRepository;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll (){
        List<Product> products = repository.findAll();

        return  products.stream().map(product -> new ProductDTO(product)).toList();

    }

    @Transactional(readOnly = true)
    public ProductDTO findById (Long id){
        Product product = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Resource not found with id " + id));
        return  new ProductDTO(product);

    }


}
