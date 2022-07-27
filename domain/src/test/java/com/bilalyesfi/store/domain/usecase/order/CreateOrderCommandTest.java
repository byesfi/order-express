package com.bilalyesfi.store.domain.usecase.order;

import com.bilalyesfi.store.domain.exception.ProductException;
import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.model.OrderLine;
import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.domain.repository.OrderRepository;
import com.bilalyesfi.store.domain.repository.ProductRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Create Order Command Unit Tests")
public class CreateOrderCommandTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateOrderCommand createOrderCommand;

    @Test
    @DisplayName("Given a valid order _When call handle method _Then call create order repository method")
    void givenValidProduct_WhenCallHandle_ThenProductCreated(){
        //Given
        List<OrderLine> orderLines = new ArrayList<>();
        OrderLine orderLine = new OrderLine(1L, 2L, 3L, 1);
        orderLines.add(orderLine);
        Order order = new Order(2L, orderLines, false, BigDecimal.valueOf(97));
        Product product = new Product(1L, "Product Test", 19, BigDecimal.valueOf(97));
        when(productRepository.get(anyLong())).thenReturn(product);

        //When
        createOrderCommand.handle(order);

        //Then
        verify(productRepository, times(1)).get(any());
        verify(orderRepository, times(1)).create(any());

    }

    @Test
    @DisplayName("Given a product without enough stock _When call handle method _Then throw ProductException")
    void givenValidProductWithoutStock_WhenCallHandle_ThenProductExceptionIsRaised(){
        //Given
        List<OrderLine> orderLines = new ArrayList<>();
        OrderLine orderLine1 = new OrderLine(1L, 2L, 3L, 20);
        OrderLine orderLine2 = new OrderLine(2L, 2L, 4L, 20);
        orderLines.add(orderLine1);
        orderLines.add(orderLine2);
        Order order = new Order(2L, orderLines, false, BigDecimal.valueOf(97));
        Product product1 = new Product(3L, "Product1 Test", 19, BigDecimal.valueOf(97));
        Product product2 = new Product(4L, "Product2 Test", 10, BigDecimal.valueOf(97));
        when(productRepository.get(3L)).thenReturn(product1);
        when(productRepository.get(4L)).thenReturn(product2);

        //When
        assertThrows(ProductException.class, () ->  createOrderCommand.handle(order));

        //Then
        verify(productRepository, times(2)).get(any());
        verify(orderRepository, never()).create(any());
    }
}
