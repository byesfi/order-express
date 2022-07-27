package com.bilalyesfi.store.entrypoint.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductRequest {

    Long id;

    @NotNull(message = "Name can't be null")
    String name;

    @NotNull(message = "Quantity can't be null")
    @Min(value = 1, message = "Product quantity can't be null or less than 1")
    Integer quantity;

    @NotNull(message = "Price can't be null")
    @Min(value = 0, message = "Product quantity can't be null or less than 0")
    BigDecimal price;

    public ProductRequest(Long id, String name, Integer quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}