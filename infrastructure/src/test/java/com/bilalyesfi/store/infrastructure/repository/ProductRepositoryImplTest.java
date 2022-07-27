package com.bilalyesfi.store.infrastructure.repository;

import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.infrastructure.entity.ProductEntity;
import com.bilalyesfi.store.infrastructure.repository.jpa.ProductJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product repository implementation Unit Tests")
public class ProductRepositoryImplTest {

    @Mock
    private ProductJpaRepository productJpaRepository;

    @InjectMocks
    ProductRepositoryImpl productRepository;

    @Test
    @DisplayName("Given a stored product id when get method is called then product is returned")
    void givenStoredProductEntity_whenGetMethodIsCalled_thenProductEntityIsReturned(){
        //Given
        ProductEntity productEntity = new ProductEntity(1L, "Manzana", 19, BigDecimal.valueOf(97));
        when(productJpaRepository.getProductEntityById(anyLong())).thenReturn(Optional.of(productEntity));

        //When
        Product product = productRepository.get(1L);

        //Then
        assertNotNull(product);
        assertEquals(productEntity.getId(), product.getId());
        assertEquals(productEntity.getName(), product.getName());
        assertEquals(productEntity.getQuantity(), product.getQuantity());
        assertEquals(productEntity.getPrice(), product.getPrice());
        verify(productJpaRepository, times(1)).getProductEntityById(anyLong());
    }

    @Test
    @DisplayName("Given a non stored productId when get method is called then null value is returned")
    void givenNonStoredProductId_whenGetMethodIsCalled_thenProductEntityIsReturned(){
        //Given
        when(productJpaRepository.getProductEntityById(anyLong())).thenReturn(Optional.empty());

        //When
        Product product = productRepository.get(1L);

        //Then
        assertNull(product);
        verify(productJpaRepository, times(1)).getProductEntityById(anyLong());
    }

    @Test
    @DisplayName("Given a product when create method is called then a product entity is created and returned")
    void givenProduct_whenCreateMethodIsCalled_thenProductEntityIsCreatedAndReturned(){
        //Given
        Product product = new Product(1L, "Manzana", 19, BigDecimal.valueOf(97));
        ProductEntity productEntity = new ProductEntity(1L, "Manzana", 19, BigDecimal.valueOf(97));
        when(productJpaRepository.save(any())).thenReturn(productEntity);

        //When
        Product savedProduct = productRepository.create(product);

        //Then
        verify(productJpaRepository, times(1)).save(any());
        assertNotNull(savedProduct);
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
        assertEquals(product.getPrice(), savedProduct.getPrice());
    }

    @Test
    @DisplayName("Given a product when update method is called then a product entity is update and returned")
    void givenProduct_whenUpdatedMethodIsCalled_thenProductEntityIsUpdatedAndReturned(){
        //Given
        Product product = new Product(1L, "Manzana", 19, BigDecimal.valueOf(97));
        ProductEntity productEntity = new ProductEntity(1L, "Manzana", 19, BigDecimal.valueOf(97));
        when(productJpaRepository.save(any())).thenReturn(productEntity);

        //When
        Product savedProduct = productRepository.update(product);

        //Then
        verify(productJpaRepository, times(1)).save(any());
        assertNotNull(savedProduct);
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
        assertEquals(product.getPrice(), savedProduct.getPrice());
    }

    @Test
    @DisplayName("Given a product when delete method is called then a product entity is deleted")
    void givenProduct_whenDeleteMethodIsCalled_thenProductEntityIsCanceledAndReturned(){
        //Given

        //When
        productRepository.delete(1L);

        //Then
        verify(productJpaRepository, times(1)).deleteProductEntityById(any());

    }

}
