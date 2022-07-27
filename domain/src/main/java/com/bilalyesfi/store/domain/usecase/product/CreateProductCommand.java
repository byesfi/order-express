package com.bilalyesfi.store.domain.usecase.product;


import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.domain.repository.ProductRepository;

public class CreateProductCommand {

  private final ProductRepository productRepository;

  public CreateProductCommand(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product handle(Product product){
      return productRepository.create(product);
  }
}
