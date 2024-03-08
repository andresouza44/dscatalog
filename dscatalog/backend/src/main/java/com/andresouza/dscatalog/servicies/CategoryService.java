package com.andresouza.dscatalog.servicies;

import com.andresouza.dscatalog.dto.CategoryDTO;
import com.andresouza.dscatalog.entities.Category;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import com.andresouza.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll (){
        List<Category> categories = repository.findAll();

        return categories.stream()
                .map(category -> new CategoryDTO(category))
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById (Long id){
        Category category = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Resource not found with id " + id));
        return  new CategoryDTO(category);

    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto){
        Category entity = new Category();
        entity.setName(dto.getName());

        repository.save(entity);

        return new CategoryDTO(entity);


    }

}
