package com.bilalyesfi.store.domain.usecase.order;


import com.bilalyesfi.store.domain.exception.ProductException;
import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.model.OrderLine;
import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.domain.repository.OrderRepository;
import com.bilalyesfi.store.domain.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateOrderCommand {

  private final OrderRepository orderRepository;

  private final ProductRepository productRepository;

  public CreateOrderCommand(OrderRepository orderRepository, ProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }

  public Order handle(Order order) throws ProductException {

    List<OrderLine> orderLines = new ArrayList<>();
    List<Product> missingProducts = new ArrayList<>();

    order.getOrderLines().stream().map(ol ->
    {
      Product product = productRepository.get(ol.getProductId());

      if (Objects.isNull(product)) {
        missingProducts.add(new Product(ol.getProductId(), null, ol.getProductQuantity(), null));
        return null;
      }

      int quantity = product.getQuantity();

      if (quantity < ol.getProductQuantity()) {
        missingProducts.add(product);
        return null;

      } else {
        return new OrderLine(ol.getId(), ol.getOrderId(), ol.getProductId(), ol.getProductQuantity());
      }

    }).filter(Objects::nonNull).forEach(orderLines::add);

    if (missingProducts.isEmpty()) {
      order.setOrderLines(orderLines);
      return orderRepository.create(order);
    } else {
      throw new ProductException(missingProducts);
    }
  }
}
