package com.bilalyesfi.store.domain.usecase.order;


import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.repository.OrderRepository;

import java.time.LocalDateTime;

public class CancelOrderCommand {

  private final OrderRepository orderRepository;

  public CancelOrderCommand(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order handle(Long orderId){
      return orderRepository.cancel(orderId);
  }

  public void removeNotPayedOrder(LocalDateTime date){
      orderRepository.removeNotPaid(date);;
  }
}
