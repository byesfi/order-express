package com.bilalyesfi.store.infrastructure.repository.jpa;

import com.bilalyesfi.store.infrastructure.entity.OrderLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineJpaRepository extends JpaRepository<OrderLineEntity, Long> {
}
