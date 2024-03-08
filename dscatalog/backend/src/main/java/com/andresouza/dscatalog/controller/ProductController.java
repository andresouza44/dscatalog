package com.andresouza.dscatalog.controller;

import com.andresouza.dscatalog.dto.CategoryDTO;
import com.andresouza.dscatalog.dto.ProductDTO;
import com.andresouza.dscatalog.servicies.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<ProductDTO> dto = service.findAll();
        return ResponseEntity.ok().body(dto);

    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<ProductDTO> findById (@PathVariable Long id){
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);

    }
}
