package com.andresouza.dscatalog.servicies;

import com.andresouza.dscatalog.dto.CategoryDTO;
import com.andresouza.dscatalog.dto.ProductDTO;
import com.andresouza.dscatalog.entities.Category;
import com.andresouza.dscatalog.entities.Product;
import com.andresouza.dscatalog.projection.IdProjection;
import com.andresouza.dscatalog.projection.ProductProjection;
import com.andresouza.dscatalog.repositories.CategoryRepository;
import com.andresouza.dscatalog.repositories.ProductRepository;
import com.andresouza.dscatalog.servicies.exceptions.DataBaseException;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import com.andresouza.dscatalog.utils.Util;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.andresouza.dscatalog.utils.Util.replace;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll (Pageable pageable){
        Page<Product> products = repository.findAll(pageable);
        return  products.map(ProductDTO::new);

    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> searchAllPageable(String name, String categoryId,Pageable pageable){

        List<Long> categoryIds = List.of();
        if (!categoryId.isEmpty()){
            List<String> list = Arrays.asList(categoryId.split(","));
            categoryIds = list.stream().map(Long::parseLong).toList();
        }

        Page<ProductProjection> page = repository.searchProducts(categoryIds,name, pageable);
        List<Long> productId = page.map(IdProjection::getId).toList();

        List<Product> entity = repository.searchProductsWithCategories(productId);
        entity = (List<Product>) Util.replace(page.getContent(),entity);

        List<ProductDTO> dtos = entity.stream().map(ProductDTO::new).toList();

        return new PageImpl<>(dtos,page.getPageable(),page.getTotalElements());
}

    @Transactional(readOnly = true)
    public ProductDTO findById (Long id){
        Product product = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Resource not found with id " + id));
        return  new ProductDTO(product);

    }


    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product product = new Product();
        product.setDate(Instant.now());
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setImgUrl(dto.getImgUrl());

        // retirei a entidade categoria para ficar igual ao do curso
      /*  dto.getCategories().forEach(categoryDTO -> {
            Category category= categoryRepository.getReferenceById(categoryDTO.getId());
            product.getCategories().add(category);
        });
*/
        repository.save(product);

        return new ProductDTO(product);

    }

    @Transactional
    public ProductDTO update (Long id, ProductDTO dto) {

        try {
            Product entity = repository.getReferenceById(id);
            if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
            if (dto.getPrice() != null) entity.setPrice(dto.getPrice());
            if (dto.getImgUrl() != null) entity.setImgUrl(dto.getImgUrl());
            if (dto.getName() != null) entity.setName(dto.getName());

            // retirei a entidade categoria para ficar igual ao do curso
          /*  if (!dto.getCategories().isEmpty()) {
                entity.getCategories().clear();
                dto.getCategories().forEach(categoryDTO ->
                                entity.getCategories().add(new Category(categoryDTO.getId())));
            }*/
            repository.save(entity);
            return new ProductDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found with id " + id);

        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deletById(Long id) {

        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Resource not found with id " + id);
        }

        try {
            repository.deleteById(id);

        }catch (DataIntegrityViolationException e){
            throw new DataBaseException("Data entity violation");
        }


    }
}





























