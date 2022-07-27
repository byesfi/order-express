package com.bilalyesfi.store.infrastructure.repository.jpa;

import com.bilalyesfi.store.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> getOrderEntityById(Long orderId);

    void deleteOrderEntityById(Long aLong);

    Stream<OrderEntity> getOrderEntitiesByIsPayedFalseAndCreatedAtIsBefore(LocalDateTime date);
}
