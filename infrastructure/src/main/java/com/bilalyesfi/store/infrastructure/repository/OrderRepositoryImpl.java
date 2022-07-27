package com.bilalyesfi.store.infrastructure.repository;


import com.bilalyesfi.store.domain.exception.ResourceNotFoundException;
import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.repository.OrderRepository;
import com.bilalyesfi.store.infrastructure.entity.OrderEntity;
import com.bilalyesfi.store.infrastructure.mapper.OrderMapper;
import com.bilalyesfi.store.infrastructure.repository.jpa.OrderJpaRepository;
import com.bilalyesfi.store.infrastructure.repository.jpa.ProductJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderJpaRepository orderJpaRepository;

  private final ProductJpaRepository productJpaRepository;

  public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository,
      ProductJpaRepository productJpaRepository) {
    this.orderJpaRepository = orderJpaRepository;
    this.productJpaRepository = productJpaRepository;
  }

  @Override
  public Order get(final Long orderId) {
    OrderEntity orderEntity = orderJpaRepository.getOrderEntityById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found " + orderId));
    return OrderMapper.mapToDomain(orderEntity);
  }

  @Override
  @Transactional
  public Order create(final Order order) {

    OrderEntity orderEntity = OrderMapper.mapToOrderEntity(order, productJpaRepository);

    orderEntity.getOrderLines().forEach(ol -> ol.getProduct().setQuantity(ol.getProduct().getQuantity() - ol.getProductQuantity()));
    OrderEntity orderSaved = orderJpaRepository.save(orderEntity);
    return OrderMapper.mapToDomain(orderSaved);
  }

  @Override
  @Transactional
  public Order cancel(final Long orderId) {
    OrderEntity orderEntity = orderJpaRepository.getOrderEntityById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
    orderEntity.getOrderLines().forEach(ol -> {
      ol.getProduct().setQuantity(ol.getProduct().getQuantity() + ol.getProductQuantity());
    });
    OrderEntity savedOrderEntity = orderJpaRepository.save(orderEntity);
    orderJpaRepository.deleteOrderEntityById(orderId);

    return OrderMapper.mapToDomain(savedOrderEntity);
  }

  @Override
  @Transactional
  public Order pay(final Order order) {
    OrderEntity orderEntity = OrderMapper.mapToOrderEntity(order, productJpaRepository);
    return OrderMapper.mapToDomain(orderJpaRepository.save(orderEntity));
  }

  @Override
  @Transactional
  public void removeNotPaid(final LocalDateTime date) {
    orderJpaRepository.getOrderEntitiesByIsPayedFalseAndCreatedAtIsBefore(date).forEach(o -> orderJpaRepository.deleteOrderEntityById(o.getId()));
  }
}
