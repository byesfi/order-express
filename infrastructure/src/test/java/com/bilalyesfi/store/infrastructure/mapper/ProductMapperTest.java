package com.bilalyesfi.store.infrastructure.mapper;

import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.infrastructure.entity.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product Mapper Unit Tests")
public class ProductMapperTest {

    @Test
    @DisplayName("Given a product entity when mapToDomain method is called then product is returned")
    void givenProductEntity_whenMapToProductEntityIsCalled_thenProductIsReturned(){
        //Given
        ProductEntity productEntity = new ProductEntity(1L, "Manzana", 19, BigDecimal.valueOf(97));

        //When
        Product product = ProductMapper.mapToDomain(productEntity);

        //Then
        Assertions.assertEquals(productEntity.getId(), product.getId());
        Assertions.assertEquals(productEntity.getName(), product.getName());
        Assertions.assertEquals(productEntity.getQuantity(), product.getQuantity());
        Assertions.assertEquals(productEntity.getPrice(), product.getPrice());
    }

    @Test
    @DisplayName("Given a product when mapToProductEntity method is called then product entity is returned")
    void givenProduct_whenMapToProductEntityIsCalled_thenProductEntityIsReturned(){
        //Given
        Product product = new Product(1L, "Manzana", 19, BigDecimal.valueOf(97));

        //When
        ProductEntity productEntity = ProductMapper.mapToProductEntity(product);

        //Then
        Assertions.assertEquals(product.getId(), productEntity.getId());
        Assertions.assertEquals(product.getName(), productEntity.getName());
        Assertions.assertEquals(product.getQuantity(), productEntity.getQuantity());
        Assertions.assertEquals(product.getPrice(), productEntity.getPrice());
    }
}
