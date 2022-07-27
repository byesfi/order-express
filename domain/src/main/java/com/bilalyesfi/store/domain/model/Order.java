package com.bilalyesfi.store.domain.model;

import java.math.BigDecimal;
import java.util.List;

public class Order {

  Long id;

  List<OrderLine> orderLines;

  Boolean isPayed;

  BigDecimal totalCost;

  public Order() {
  }

  public Order(Long id, List<OrderLine> orderLines, Boolean isPayed, BigDecimal totalCost) {
    this.id = id;
    this.orderLines = orderLines;
    this.isPayed = isPayed;
    this.totalCost = totalCost;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<OrderLine> getOrderLines() {
    return orderLines;
  }

  public void setOrderLines(List<OrderLine> orderLines) {
    this.orderLines = orderLines;
  }

  public Boolean getIsPayed() {
    return isPayed;
  }

  public void setIsPayed(Boolean payed) {
    isPayed = payed;
  }

  public BigDecimal getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(BigDecimal totalCost) {
    this.totalCost = totalCost;
  }
}
