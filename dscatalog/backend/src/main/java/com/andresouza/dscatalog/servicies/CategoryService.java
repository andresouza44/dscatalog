package com.andresouza.dscatalog.servicies;

import com.andresouza.dscatalog.dto.CategoryDTO;
import com.andresouza.dscatalog.entities.Category;
import com.andresouza.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository service;

    public List<CategoryDTO> findAll (){
        List<Category> categories = service.findAll();

        return categories.stream()
                .map(category -> new CategoryDTO(category))
                .toList();
    }

}
