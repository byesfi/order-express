package com.bilalyesfi.store.domain.usecase.product;

import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.domain.repository.ProductRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Update Product Command Unit Tests")
class UpdateProductCommandTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductCommand updateProductCommand;

    @Test
    @DisplayName("Given existing product when call update product service then call update product repository method is called")
    void givenExistingProduct_whenCallUpdateProductEndpoint_thenUpdateProductRepositoryMethodIsCalled(){
        //Given
        Product product = new Product(1L, "Product Test", 19, BigDecimal.valueOf(97));
        when(productRepository.get(any())).thenReturn(product);

        //When
        updateProductCommand.handle(product);

        //Then
        verify(productRepository, times(1)).get(1L);
        verify(productRepository, times(1)).update(any());
    }

    @Test
    @DisplayName("Given non existing product when call update product service then update product repository method is called")
    void givenNonExistingProduct_whenCallUpdateProductService_thenUpdateProductRepositoryMethodIsCalled(){
        //Given
        Product product = new Product(1L, "Product Test", 19, BigDecimal.valueOf(97));
        when(productRepository.get(any())).thenReturn(null);

        //When
        updateProductCommand.handle(product);

        //Then
        verify(productRepository, times(1)).get(1L);
        verify(productRepository, times(1)).update(any());
    }
}
