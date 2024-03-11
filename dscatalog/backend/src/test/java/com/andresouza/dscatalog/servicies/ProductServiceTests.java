package com.andresouza.dscatalog.servicies;

import com.andresouza.dscatalog.repositories.ProductRepository;
import com.andresouza.dscatalog.servicies.exceptions.DataBaseException;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private Long existId;
    private Long nonExistId;
    private Long dependentId;

    @BeforeEach
    void setUp() {
        existId = 1L;
        nonExistId = 1000L;
        dependentId = 2L;

        Mockito.when(repository.existsById(existId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistId)).thenReturn(false);
        Mockito.when(repository.existsById(dependentId)).thenReturn(true);

        Mockito.doThrow(DataBaseException.class).when(repository).deleteById(dependentId);
        Mockito.doNothing().when(repository).deleteById(existId);




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


