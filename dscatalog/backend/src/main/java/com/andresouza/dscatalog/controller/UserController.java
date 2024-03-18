package com.andresouza.dscatalog.controller;

import com.andresouza.dscatalog.dto.UserDto;
import com.andresouza.dscatalog.dto.UserInsertDTO;
import com.andresouza.dscatalog.dto.UserUpdateDTO;
import com.andresouza.dscatalog.servicies.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    UserService service;

    @PreAuthorize("hasRole ('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(Pageable pageable){
        Page<UserDto> dtoPage= service.findAll(pageable);
        return ResponseEntity.ok().body(dtoPage);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id){
        UserDto dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity insert (@Valid @RequestBody UserInsertDTO dto){
        UserDto userDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }
    @PreAuthorize("hasRole ('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> update (@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto){
        UserDto newDto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
