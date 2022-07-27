package com.bilalyesfi.store.domain.usecase.order;

import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.repository.OrderRepository;


public class PayOrderCommand {

  private final OrderRepository orderRepository;

  public PayOrderCommand(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order handle(Long orderId) {
    Order order = orderRepository.get(orderId);
    order.setIsPayed(true);
    return orderRepository.pay(order);
  }
}
