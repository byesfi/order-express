package com.bilalyesfi.store.domain.repository;

import com.bilalyesfi.store.domain.model.Product;


public interface ProductRepository {

  Product get(final Long id);

  Product create(final Product product);

  Product update(final Product product);

  void delete(final Long id);
}
