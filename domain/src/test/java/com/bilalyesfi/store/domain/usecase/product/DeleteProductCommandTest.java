package com.bilalyesfi.store.domain.usecase.product;

import com.bilalyesfi.store.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Delete Product Command Unit Tests")
public class DeleteProductCommandTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductCommand deleteProductCommand;

    @Test
    @DisplayName("Given a productId When Call delete product service then delete product repository is called")
    void givenProductId_whenCallDeleteProduct_thenDeleteProductRepositoryMethodIsCalled(){
        deleteProductCommand.handle(2L);

        verify(productRepository, times(1)).delete(2L);
    }
}
