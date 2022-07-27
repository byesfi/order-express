package com.bilalyesfi.store.infrastructure.repository.jpa;

import com.bilalyesfi.store.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> getProductEntityById(Long id);

  void deleteProductEntityById(Long id);

}
