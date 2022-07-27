package com.bilalyesfi.store.infrastructure.mapper;

import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.model.OrderLine;
import com.bilalyesfi.store.infrastructure.entity.OrderEntity;
import com.bilalyesfi.store.infrastructure.entity.OrderLineEntity;
import com.bilalyesfi.store.infrastructure.entity.ProductEntity;
import com.bilalyesfi.store.infrastructure.repository.jpa.ProductJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Order Mapper Unit Tests")
public class OrderMapperTest {

    @Mock
    private ProductJpaRepository productJpaRepository;

    @Test
    @DisplayName("Given an order when call mapToDomain then order entity is returned")
    void givenOrder_whenCallMapToOrderEntityThenOrderEntityIsReturned(){
        //Given
        List<OrderLine> orderLineList = List.of(new OrderLine(3L, 2L, 1L, 19));
        Order order = new Order(2L, orderLineList, false, BigDecimal.valueOf(1843L) );

        ProductEntity productEntity = new ProductEntity(1L, "Manzana", 19, BigDecimal.valueOf(97));
        when(productJpaRepository.getProductEntityById(1L)).thenReturn(Optional.of(productEntity));

        //When
        OrderEntity orderEntity = OrderMapper.mapToOrderEntity(order, productJpaRepository);

        //Then
        assertEquals(order.getTotalCost(), orderEntity.getTotalCost());
        assertEquals(order.getOrderLines().size(), orderEntity.getOrderLines().size());
        assertEquals(order.getOrderLines().get(0).getProductId(), orderEntity.getOrderLines().get(0).getProduct().getId());
        assertEquals(order.getOrderLines().get(0).getProductQuantity(), orderEntity.getOrderLines().get(0).getProductQuantity());
        verify(productJpaRepository, times(1)).getProductEntityById(anyLong());
    }

    @Test
    @DisplayName("Given an order entity when call mapToDomain then order returned")
    void givenOrderEntity_whenCallMapToDomainThenOrderEntityIsReturned(){
        //Given
        ProductEntity productEntity = new ProductEntity(1L, "Manzana", 19, BigDecimal.valueOf(97));
        OrderEntity orderEntity = new OrderEntity();
        List<OrderLineEntity> orderLineEntities = List.of(new OrderLineEntity( orderEntity, productEntity, 19));
        orderEntity.setOrderLines(orderLineEntities);
        orderEntity.setTotalCost(BigDecimal.valueOf(1843L));
        orderEntity.setIsPayed(false);

        //When
        Order order = OrderMapper.mapToDomain(orderEntity);

        //Then
        assertEquals(orderEntity.getId(), order.getId());
        assertEquals(orderEntity.getTotalCost(), order.getTotalCost());
        assertEquals(orderEntity.getOrderLines().size(), order.getOrderLines().size());
        assertEquals(orderEntity.getOrderLines().get(0).getId(), order.getOrderLines().get(0).getId());
        assertEquals(orderEntity.getOrderLines().get(0).getOrder().getId(), order.getOrderLines().get(0).getOrderId());
        assertEquals(orderEntity.getOrderLines().get(0).getProduct().getId(), order.getOrderLines().get(0).getProductId());
        assertEquals(orderEntity.getOrderLines().get(0).getProductQuantity(), order.getOrderLines().get(0).getProductQuantity());
    }
}
