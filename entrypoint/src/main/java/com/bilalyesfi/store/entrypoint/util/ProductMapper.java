package com.bilalyesfi.store.entrypoint.util;

import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.entrypoint.dto.ProductRequest;

public class ProductMapper {

    public static Product mapToDomain(final ProductRequest productRequest) {
        return new Product(productRequest.getId(), productRequest.getName(), productRequest.getQuantity(), productRequest.getPrice());
    }
}
