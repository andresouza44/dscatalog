package com.andresouza.dscatalog.servicies;

import com.andresouza.dscatalog.dto.CategoryDTO;
import com.andresouza.dscatalog.entities.Category;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import com.andresouza.dscatalog.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category category = repository.getReferenceById(id);
            if(dto.getName() != null) category.setName(dto.getName());

            return  new CategoryDTO(category);

        }catch (EntityNotFoundException e ){
            throw  new ResourceNotFoundException("Not found");
        }

    }

    public void  deleteById (Long id){
       if (!repository.existsById(id)){
           throw new ResourceNotFoundException("Resource not found");
       }
        try{

            repository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Data integrity violation");
        }
    }
}
