package com.bilalyesfi.store.domain.usecase.product;

import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.domain.repository.ProductRepository;

import java.util.Objects;

public class UpdateProductCommand {

  private final ProductRepository productRepository;

  public UpdateProductCommand(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product handle(final Product product) {

    Product savedProduct = productRepository.get(product.getId());

    if (Objects.isNull(savedProduct)) {
      Product newProduct = new Product(null, product.getName(), product.getQuantity(), product.getPrice());
      return productRepository.update(newProduct);
    } else {
      savedProduct.setName(product.getName());
      savedProduct.setQuantity(product.getQuantity());
      savedProduct.setPrice(product.getPrice());
      return productRepository.update(savedProduct);
    }
  }
}
