package com.bilalyesfi.store.infrastructure.mapper;


import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.model.OrderLine;
import com.bilalyesfi.store.infrastructure.entity.OrderEntity;
import com.bilalyesfi.store.infrastructure.entity.OrderLineEntity;
import com.bilalyesfi.store.infrastructure.entity.ProductEntity;
import com.bilalyesfi.store.infrastructure.repository.jpa.ProductJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OrderMapper {

  public static Order mapToDomain(final OrderEntity orderEntity) {
    List<OrderLine> orderLines = new ArrayList<>();
    orderEntity.getOrderLines().stream()
            .map(ol -> new OrderLine(ol.getId(), ol.getOrder().getId(), ol.getProduct().getId(), ol.getProductQuantity()))
            .forEach(orderLines::add);
    return new Order(orderEntity.getId(), orderLines, orderEntity.getIsPayed(), orderEntity.getTotalCost());
  }

  public static OrderEntity mapToOrderEntity(final Order order, final ProductJpaRepository productRepository) {
    List<OrderLineEntity> orderLines = new ArrayList<>();
    OrderEntity orderEntity = new OrderEntity(orderLines, order.getTotalCost());
    orderEntity.setIsPayed(order.getIsPayed());

    order.getOrderLines().stream()
            .map(ol -> {
              Optional<ProductEntity> productEntityOptional = productRepository.getProductEntityById(ol.getProductId());
                return productEntityOptional.map(productEntity -> new OrderLineEntity(orderEntity, productEntity, ol.getProductQuantity())).orElse(null);
            })
            .filter(Objects::nonNull).forEach(orderLines::add);

   return orderEntity;
  }
}
