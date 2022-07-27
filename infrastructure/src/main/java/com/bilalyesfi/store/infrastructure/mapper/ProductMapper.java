package com.bilalyesfi.store.infrastructure.mapper;

import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.infrastructure.entity.ProductEntity;

public class ProductMapper {

  public static Product mapToDomain(final ProductEntity productEntity) {
    return new Product(productEntity.getId(), productEntity.getName(), productEntity.getQuantity(), productEntity.getPrice());
  }

  public static ProductEntity mapToProductEntity(final Product product) {
    return new ProductEntity(product.getId(), product.getName(), product.getQuantity(), product.getPrice());
  }
}
