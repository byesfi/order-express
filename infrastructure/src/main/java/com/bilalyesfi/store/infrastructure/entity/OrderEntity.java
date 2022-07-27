package com.bilalyesfi.store.infrastructure.entity;

import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity(name = "orders")
public class OrderEntity {

  @Id
  @SequenceGenerator(
          name = "order_id_sequence",
          sequenceName = "order_id_sequence"
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "order_id_sequence"
  )
  private Long id;

  @OneToMany(mappedBy = "order",  cascade = CascadeType.ALL)
  private List<OrderLineEntity> orderLines;

  private BigDecimal totalCost;

  private Boolean isPayed = false;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updateAt;

  public OrderEntity() {
  }

  public OrderEntity(List<OrderLineEntity> orderLineEntities, BigDecimal totalCost){
    this.orderLines = orderLineEntities;
    this.totalCost = totalCost;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<OrderLineEntity> getOrderLines() {
    return orderLines;
  }

  public void setOrderLines(List<OrderLineEntity> orderLines) {
    this.orderLines = orderLines;
  }

  public BigDecimal getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(BigDecimal totalCost) {
    this.totalCost = totalCost;
  }

  public Boolean getIsPayed() {
    return isPayed;
  }

  public void setIsPayed(Boolean payed) {
    isPayed = payed;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(LocalDateTime updateAt) {
    this.updateAt = updateAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    OrderEntity that = (OrderEntity) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
