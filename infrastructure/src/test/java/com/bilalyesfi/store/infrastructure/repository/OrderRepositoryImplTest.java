package com.bilalyesfi.store.infrastructure.repository;

import com.bilalyesfi.store.domain.exception.ResourceNotFoundException;
import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.model.OrderLine;
import com.bilalyesfi.store.infrastructure.entity.OrderEntity;
import com.bilalyesfi.store.infrastructure.entity.OrderLineEntity;
import com.bilalyesfi.store.infrastructure.entity.ProductEntity;
import com.bilalyesfi.store.infrastructure.repository.jpa.OrderJpaRepository;
import com.bilalyesfi.store.infrastructure.repository.jpa.ProductJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Order repository implementation Unit Tests")
public class OrderRepositoryImplTest {

    @Mock
    private OrderJpaRepository orderJpaRepository;

    @Mock
    private ProductJpaRepository productJpaRepository;

    @InjectMocks
    private OrderRepositoryImpl orderRepository;

    @Test
    @DisplayName("Given an orderId of stored order when get method is called then order is returned")
    void givenOrderId_whenGetMethodIsCalled_thenOrderIsReturned(){
        //Given
        ProductEntity productEntity = new ProductEntity(1L, "Manzana", 19, BigDecimal.valueOf(97));
        OrderEntity orderEntity = new OrderEntity();
        List<OrderLineEntity> orderLineEntities = List.of(new OrderLineEntity( orderEntity, productEntity, 19));
        orderEntity.setId(2L);
        orderEntity.setOrderLines(orderLineEntities);
        orderEntity.setTotalCost(BigDecimal.valueOf(1843L));
        orderEntity.setIsPayed(false);
        when(orderJpaRepository.getOrderEntityById(anyLong())).thenReturn(Optional.of(orderEntity));

        //When
        Order order = orderRepository.get(2L);

        //Then
        assertEquals(order.getId(), order.getId());
        assertEquals(order.getIsPayed(), order.getIsPayed());
        assertEquals(order.getOrderLines().size(), order.getOrderLines().size());
        assertEquals(order.getOrderLines().get(0).getOrderId(), order.getOrderLines().get(0).getOrderId());
        assertEquals(order.getOrderLines().get(0).getProductId(), order.getOrderLines().get(0).getProductId());
        assertEquals(order.getOrderLines().get(0).getProductQuantity(), order.getOrderLines().get(0).getProductQuantity());
        verify(orderJpaRepository, times(1)).getOrderEntityById(anyLong());
    }

    @Test
    @DisplayName("Given an orderId of non stored order when get method is called then ResourceNotFoundException is raised")
    void givenOrderIdOfNonStoredOrder_whenGetMethodIsCalled_thenResourceNotFoundExceptionRaised(){
        //Given
        //When Then
        assertThrows(ResourceNotFoundException.class, () -> orderRepository.get(2L));

        verify(orderJpaRepository, times(1)).getOrderEntityById(anyLong());
    }

    @Test
    @DisplayName("Given an order when create method is called then order is created")
    void givenAnOrder_whenCreateMethodIsCalled_thenOrderIsCreated(){
        //Given
        ProductEntity productEntity = new ProductEntity(1L, "Manzana", 19, BigDecimal.valueOf(97));
        OrderEntity orderEntity = new OrderEntity();
        List<OrderLineEntity> orderLineEntities = List.of(new OrderLineEntity( orderEntity, productEntity, 19));
        orderEntity.setId(3L);
        orderEntity.setOrderLines(orderLineEntities);
        orderEntity.setTotalCost(BigDecimal.valueOf(1843L));
        orderEntity.setIsPayed(false);

        Order order = new Order(2L, List.of(new OrderLine(2L, 3L, 1L, 18)), false, BigDecimal.valueOf(97));

        when(orderJpaRepository.save(any())).thenReturn(orderEntity);
        when(productJpaRepository.getProductEntityById(anyLong())).thenReturn(Optional.of(productEntity));

        //When
        Order savedOrder = orderRepository.create(order);

        //Then
        assertNotNull(savedOrder);
        verify(productJpaRepository, times(1)).getProductEntityById(anyLong());
        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);
        verify(orderJpaRepository, times(1)).save(captor.capture());
        OrderEntity captureOrderEntity = captor.getValue();
        assertEquals(1, captureOrderEntity.getOrderLines().get(0).getProduct().getQuantity());
    }

    @Test
    @DisplayName("Given an orderId of an existing order when cancel method is called then order is deleted")
    void givenAnOrderId_whenCancelMethodIsCalledThenOrderIsDeleted(){
        //Given
        ProductEntity productEntity = new ProductEntity(1L, "Manzana", 19, BigDecimal.valueOf(97));
        OrderEntity orderEntity = new OrderEntity();
        List<OrderLineEntity> orderLineEntities = List.of(new OrderLineEntity( orderEntity, productEntity, 19));
        orderEntity.setId(3L);
        orderEntity.setOrderLines(orderLineEntities);
        orderEntity.setTotalCost(BigDecimal.valueOf(1843L));
        orderEntity.setIsPayed(false);
        when(orderJpaRepository.getOrderEntityById(any())).thenReturn(Optional.of(orderEntity));
        when(orderJpaRepository.save(any())).thenReturn(orderEntity);

        //When
        Order canceledOrder = orderRepository.cancel(3L);

        //Then
        assertNotNull(canceledOrder);
        verify(orderJpaRepository, times(1)).getOrderEntityById(anyLong());
        verify(orderJpaRepository, times(1)).deleteOrderEntityById(anyLong());
        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);
        verify(orderJpaRepository, times(1)).save(captor.capture());
        OrderEntity captureOrderEntity = captor.getValue();
        assertEquals(38, captureOrderEntity.getOrderLines().get(0).getProduct().getQuantity());
    }

    @Test
    @DisplayName("Given an orderId of a non existing order when cancel method is called then resource not found exception is raised")
    void givenAnOrderIdOfNonExistingOrder_whenCancelMethodIsCalledThenExceptionRaised(){
        //Given
        //When
        assertThrows(ResourceNotFoundException.class, () -> orderRepository.cancel(3L));

        //Then
        verify(orderJpaRepository, times(1)).getOrderEntityById(anyLong());
        verify(orderJpaRepository, times(0)).deleteOrderEntityById(anyLong());
        verify(orderJpaRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Given an orderId of a not payed order when pay method is called then order is marked as payed and stored")
    void givenAnOrderIdOfExistingNotPayedOrder_whenPayMethodIsCalledThenOrderIsMarkedAsPayed(){
        //Given
        ProductEntity productEntity = new ProductEntity(1L, "Manzana", 19, BigDecimal.valueOf(97));
        OrderEntity orderEntity = new OrderEntity();
        List<OrderLineEntity> orderLineEntities = List.of(new OrderLineEntity( orderEntity, productEntity, 19));
        orderEntity.setId(3L);
        orderEntity.setOrderLines(orderLineEntities);
        orderEntity.setTotalCost(BigDecimal.valueOf(1843L));
        orderEntity.setIsPayed(true);
        when(productJpaRepository.getProductEntityById(anyLong())).thenReturn(Optional.of(productEntity));
        when(orderJpaRepository.save(any())).thenReturn(orderEntity);
        Order order = new Order(2L, List.of(new OrderLine(2L, 3L, 1L, 18)), true, BigDecimal.valueOf(97));


        //When
        Order payedOrder = orderRepository.pay(order);

        //Then
        assertNotNull(payedOrder);
        verify(productJpaRepository, times(1)).getProductEntityById(anyLong());
        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);
        verify(orderJpaRepository, times(1)).save(captor.capture());
        OrderEntity captureOrderEntity = captor.getValue();
        assertTrue(captureOrderEntity.getIsPayed());
    }

    @Test
    @DisplayName("Given an orderId of a not 30 minutes no payed order when remove not payed method is called then order is removed")
    void givenAnOrderIdOfExistingNotPayedOrder_whenRemoveNotPayedMethodIsCalledThenRemoveOldNotPayedOrders(){
        //Given
        ProductEntity productEntity = new ProductEntity(1L, "Manzana", 19, BigDecimal.valueOf(97));
        OrderEntity orderEntity = new OrderEntity();
        List<OrderLineEntity> orderLineEntities = List.of(new OrderLineEntity( orderEntity, productEntity, 19));
        orderEntity.setId(3L);
        orderEntity.setOrderLines(orderLineEntities);
        orderEntity.setTotalCost(BigDecimal.valueOf(1843L));
        orderEntity.setIsPayed(true);
        when(orderJpaRepository.getOrderEntitiesByIsPayedFalseAndCreatedAtIsBefore(any())).thenReturn(Stream.of(orderEntity));


        //When
        orderRepository.removeNotPaid(LocalDateTime.now().minusMinutes(30));

        //Then
        verify(orderJpaRepository, times(1)).getOrderEntitiesByIsPayedFalseAndCreatedAtIsBefore(any());
        verify(orderJpaRepository, times(1)).deleteOrderEntityById(anyLong());
    }
}
