package com.bilalyesfi.store.domain.repository;

import com.bilalyesfi.store.domain.model.Order;

import java.time.LocalDateTime;

public interface OrderRepository {

  Order get(final Long orderId);

  Order create(final Order order);

  Order cancel(final Long orderId);

  Order pay(final Order order);

  void removeNotPaid(final LocalDateTime date);
}
