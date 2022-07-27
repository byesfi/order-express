package com.bilalyesfi.store.infrastructure.repository;

import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.domain.repository.ProductRepository;
import com.bilalyesfi.store.infrastructure.entity.ProductEntity;
import com.bilalyesfi.store.infrastructure.mapper.ProductMapper;
import com.bilalyesfi.store.infrastructure.repository.jpa.ProductJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

  private final ProductJpaRepository productJpaRepository;

  public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) {
    this.productJpaRepository = productJpaRepository;
  }

  @Override
  public Product get(final Long id) {
    Optional<ProductEntity> productEntityOptional = productJpaRepository.getProductEntityById(id);
    return productEntityOptional.map(ProductMapper::mapToDomain).orElse(null);
  }

  @Override
  @Transactional
  public Product create(final Product product) {

    ProductEntity productEntity = ProductMapper.mapToProductEntity(product);

    ProductEntity savedProductEntity = productJpaRepository.save(productEntity);

    return ProductMapper.mapToDomain(savedProductEntity);
  }

  @Override
  @Transactional
  public Product update(final Product product) {

    ProductEntity productEntity = ProductMapper.mapToProductEntity(product);

    ProductEntity savedProductEntity = productJpaRepository.save(productEntity);

    return ProductMapper.mapToDomain(savedProductEntity);
  }

  @Override
  @Transactional
  public void delete(Long productId) {
    productJpaRepository.deleteProductEntityById(productId);
  }
}
