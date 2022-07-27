package com.bilalyesfi.store.config;

import com.bilalyesfi.store.domain.repository.ProductRepository;
import com.bilalyesfi.store.domain.usecase.product.CreateProductCommand;
import com.bilalyesfi.store.domain.usecase.product.DeleteProductCommand;
import com.bilalyesfi.store.domain.usecase.product.UpdateProductCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCasesConfig {

    private final ProductRepository productRepository;

    public ProductUseCasesConfig(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Bean
    public CreateProductCommand getCreateProductCommand() {
        return new CreateProductCommand(productRepository);
    }

    @Bean
    public UpdateProductCommand getUpdateProductCommand() {
        return new UpdateProductCommand(productRepository);
    }

    @Bean
    public DeleteProductCommand getDeleteProductCommand() {
        return new DeleteProductCommand(productRepository);
    }
}
