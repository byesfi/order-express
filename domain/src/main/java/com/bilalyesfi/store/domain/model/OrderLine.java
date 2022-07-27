package com.bilalyesfi.store.domain.model;

public class OrderLine {

  Long id;

  Long orderId;

  Long productId;

  int productQuantity;

  public OrderLine() {
  }

  public OrderLine(Long id, Long orderId, Long productId, int productQuantity) {
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
