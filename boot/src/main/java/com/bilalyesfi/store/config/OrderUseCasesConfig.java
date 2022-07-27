package com.bilalyesfi.store.config;

import com.bilalyesfi.store.domain.repository.OrderRepository;
import com.bilalyesfi.store.domain.repository.ProductRepository;
import com.bilalyesfi.store.domain.usecase.order.CreateOrderCommand;
import com.bilalyesfi.store.domain.usecase.order.CancelOrderCommand;
import com.bilalyesfi.store.domain.usecase.order.PayOrderCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderUseCasesConfig {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    public OrderUseCasesConfig(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Bean
    public CreateOrderCommand getCreateOrderCommand() {
        return new CreateOrderCommand(orderRepository, productRepository);
    }

    @Bean
    public PayOrderCommand getUpdateOrderCommand() {
        return new PayOrderCommand(orderRepository);
    }

    @Bean
    public CancelOrderCommand getDeleteOrderCommand() {
        return new CancelOrderCommand(orderRepository);
    }
}
