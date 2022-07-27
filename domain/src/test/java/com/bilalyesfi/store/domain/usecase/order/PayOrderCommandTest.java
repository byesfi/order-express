package com.bilalyesfi.store.domain.usecase.order;

import com.bilalyesfi.store.domain.exception.ResourceNotFoundException;
import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.model.OrderLine;
import com.bilalyesfi.store.domain.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pay Order Command Unit Test")
public class PayOrderCommandTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PayOrderCommand payOrderCommand;

    @Test
    @DisplayName("Given a not paid order when call payed order the order is marked as payed")
    void givenNotPaidOrder_whenCallPayOrderEndpoint_thenTheOrderIsMarkedAsPayed(){
        //Given
        List<OrderLine> orderLines = new ArrayList<>();
        OrderLine orderLine = new OrderLine(1L, 2L, 3L, 1);
        orderLines.add(orderLine);
        Order order = new Order(2L, orderLines, false, BigDecimal.valueOf(97));
        when(orderRepository.get(2L)).thenReturn(order);

        //When
        payOrderCommand.handle(2L);

        //Then
        verify(orderRepository, times(1)).get(2L);
        verify(orderRepository, times(1)).pay(any());
    }

    @Test
    @DisplayName("Given a not existing order when call payed order then return null")
    void givenNotExistingOrder_whenCallPayOrderEndpoint_thenReturnNull(){
        //Given
        List<OrderLine> orderLines = new ArrayList<>();
        OrderLine orderLine = new OrderLine(1L, 2L, 3L, 1);
        orderLines.add(orderLine);
        Order order = new Order(2L, orderLines, false, BigDecimal.valueOf(97));
        when(orderRepository.get(2L)).thenThrow(ResourceNotFoundException.class);

        //When
        assertThrows(ResourceNotFoundException.class, () -> payOrderCommand.handle(2L));

        //Then
        verify(orderRepository, never()).pay(any());
    }
}
