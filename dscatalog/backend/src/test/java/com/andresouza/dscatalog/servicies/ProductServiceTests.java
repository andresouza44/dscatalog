package com.andresouza.dscatalog.servicies;

import com.andresouza.dscatalog.dto.ProductDTO;
import com.andresouza.dscatalog.entities.Category;
import com.andresouza.dscatalog.entities.Product;
import com.andresouza.dscatalog.repositories.CategoryRepository;
import com.andresouza.dscatalog.repositories.ProductRepository;
import com.andresouza.dscatalog.servicies.exceptions.DataBaseException;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import com.andresouza.dscatalog.tests.factory.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private Long existId;
    private Long nonExistId;
    private Long dependentId;
    private Product product;
    private ProductDTO productDTO;
    private Category category;
    private PageImpl page;


    @BeforeEach
    void setUp() {
        existId = 1L;
        nonExistId = 1000L;
        dependentId = 2L;
        product = Factory.createProduct();
        productDTO = Factory.createProductDTO();
        category = Factory.createCategory();
        page = new PageImpl<>(List.of(product));

        Mockito.when(repository.existsById(existId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistId)).thenReturn(false);
        Mockito.when(repository.existsById(dependentId)).thenReturn(true);

        Mockito.doThrow(DataBaseException.class).when(repository).deleteById(dependentId);
        Mockito.doNothing().when(repository).deleteById(existId);

        Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        Mockito.when(repository.findById(existId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistId)).thenReturn(Optional.empty());

        Mockito.when(repository.getReferenceById(existId)).thenReturn(product);
        Mockito.when(repository.getReferenceById(nonExistId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(categoryRepository.getReferenceById(existId)).thenReturn(category);
        Mockito.when(categoryRepository.getReferenceById(nonExistId)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    public void updateShouldReturnResourceNotFoundExceptionWhenDoesNotExistId(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ProductDTO dto = service.update(nonExistId, productDTO);
        });
        Mockito.verify(repository).getReferenceById(nonExistId);
    }

    @Test
    public void updateShouldReturnProductDTOWhenExistedId(){
        ProductDTO dto = service.update(existId, productDTO);
        Assertions.assertNotNull(dto);

        Mockito.verify(repository).getReferenceById(existId);
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () ->{
           service.findById(nonExistId);
        });

    }
    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists(){

        ProductDTO productDTO = service.findById(existId);

        Assertions.assertNotNull(productDTO);
        Mockito.verify(repository).findById(existId);
    }

    @Test
    public void findAllPagedShouldReturnPage(){
        Pageable pageable = PageRequest.of(0,10);
        Page<ProductDTO> result = service.findAll(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(repository).findAll(pageable);

    }

    @Test
    public void deleteByIdShouldDoNothingWhenExitedId(){

        Assertions.assertDoesNotThrow(() -> {
            service.deletById(existId);
        });

        Mockito.verify(repository, times(1)).deleteById(existId);
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenDoesNotExistId(){

        Assertions.assertThrows(ResourceNotFoundException.class, ()->{
           service.deletById(nonExistId);
        });


    }

    @Test
    public void deleteByIdShouldThrowDataBaseExceptionWhenDependentId(){

        Assertions.assertThrows(DataBaseException.class, () ->{
           service.deletById(dependentId);
        });

        Mockito.verify(repository,times(1)).deleteById(dependentId);


    }



}


