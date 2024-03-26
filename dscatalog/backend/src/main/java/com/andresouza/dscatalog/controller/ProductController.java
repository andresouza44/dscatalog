package com.andresouza.dscatalog.controller;

import com.andresouza.dscatalog.dto.ProductDTO;
import com.andresouza.dscatalog.projection.ProductProjection;
import com.andresouza.dscatalog.servicies.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController

@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;


     @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "categoryId", defaultValue = "") String categoryId,
            Pageable pageable) {
        Page<ProductDTO> page = service.searchAllPageable(name, categoryId, pageable);
        return ResponseEntity.ok().body(page);

    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);

    }

    @PreAuthorize("hasAnyRole ('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @PostMapping
    public ResponseEntity<ProductDTO> insert (@Valid @RequestBody ProductDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasAnyRole ('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity <ProductDTO> update (@PathVariable Long id,@Valid @RequestBody ProductDTO dto){
        dto = service.update(id, dto);

        return ResponseEntity.ok().body(dto);


    }

    @PreAuthorize("hasAnyRole ('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete (@PathVariable Long id){
        service.deletById(id);

        return ResponseEntity.noContent().build();


    }

}



















