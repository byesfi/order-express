package com.bilalyesfi.store.domain.exception;

import com.bilalyesfi.store.domain.model.Product;

import java.util.List;

public class ProductException extends RuntimeException {

    private List<Product> missingProducts;

    public ProductException(List<Product> missingProducts) {
        super("The missing products are %s");
        this.missingProducts = missingProducts;
    }

    @Override
    public String getMessage() {
        return String.format(super.getMessage(), missingProducts.toString());
    }
}
