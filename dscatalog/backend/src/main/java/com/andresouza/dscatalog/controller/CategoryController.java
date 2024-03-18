package com.andresouza.dscatalog.controller;


import com.andresouza.dscatalog.dto.CategoryDTO;
import com.andresouza.dscatalog.entities.Category;
import com.andresouza.dscatalog.servicies.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable){
        Page<CategoryDTO> page = service.findAll(pageable);
        return ResponseEntity.ok().body(page);

    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<CategoryDTO> findById (@PathVariable Long id){
        CategoryDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);

    }

    @PreAuthorize("hasAnyRole ('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @PostMapping
    public ResponseEntity<CategoryDTO> insert (@Valid  @RequestBody CategoryDTO dto){
        CategoryDTO categoryDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(categoryDto.getId()).toUri();

        return ResponseEntity.created(uri).body(categoryDto);

    }

    @PreAuthorize("hasAnyRole ('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @PutMapping (value = "/{id}")
    public ResponseEntity<CategoryDTO> update (@PathVariable Long id,@Valid @RequestBody CategoryDTO dto){
        dto = service.update(id, dto);

        return ResponseEntity.ok().body(dto);

    }

    @PreAuthorize("hasAnyRole ('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
