package com.bilalyesfi.store.domain.usecase.product;


import com.bilalyesfi.store.domain.repository.ProductRepository;

public class DeleteProductCommand {

  private final ProductRepository productRepository;

  public DeleteProductCommand(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public void handle(Long productId){
    productRepository.delete(productId);
  }
}
