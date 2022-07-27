package com.bilalyesfi.store.domain.usecase.product;

import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Create Product Command Unit Tests")
public class CreateProductCommandTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductCommand createProductCommand;

    @Test
    @DisplayName("Given a valid product when call create product service then create product repository method is called")
    void givenValidProductWhenCallCreateProductThenCallCreateProductRepositoryMethod(){
        //Given
        Product product = new Product(null, "Product Test", 20, BigDecimal.valueOf(20));

        //When
        createProductCommand.handle(product);

        //Then
        verify(productRepository, times(1)).create(any());
    }
}
