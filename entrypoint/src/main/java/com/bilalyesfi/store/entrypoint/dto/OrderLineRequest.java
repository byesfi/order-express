package com.bilalyesfi.store.entrypoint.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderLineRequest {

    Long id;

    Long orderId;

    @NotNull(message = "ProductId can't be null")
    Long productId;

    @NotNull(message = "ProductQuantity can't be null")
    @Min(value = 1, message = "Product quantity can't be null or less than 1")
    int productQuantity;

    public OrderLineRequest(Long id, Long orderId, Long productId, int productQuantity) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
