package com.bilalyesfi.store.domain.usecase.order;

import com.bilalyesfi.store.domain.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@DisplayName("Cancel Order Cammand Unit Tests")
public class CancelOrderCommandTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CancelOrderCommand cancelOrderCommand;

    @Test
    @DisplayName("Cancel existing order")
    void givenOrderId_whenCallCancelOrder_thenOrderIsCanceled(){
        //Given
        Long orderId = 2L;

        //When
        cancelOrderCommand.handle(orderId);

        //Then
        verify(orderRepository, times(1)).cancel(2L);
    }
}
