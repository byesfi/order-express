package com.bilalyesfi.store;

import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.model.OrderLine;
import com.bilalyesfi.store.entrypoint.dto.OrderLineRequest;
import com.bilalyesfi.store.entrypoint.dto.OrderRequest;
import com.bilalyesfi.store.infrastructure.entity.OrderEntity;
import com.bilalyesfi.store.infrastructure.entity.OrderLineEntity;
import com.bilalyesfi.store.infrastructure.entity.ProductEntity;
import com.bilalyesfi.store.infrastructure.repository.jpa.OrderJpaRepository;
import com.bilalyesfi.store.infrastructure.repository.jpa.ProductJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StoreApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Order API Controller V1 Integration Test")
public class OrderApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    @DisplayName("Given a valid order when call create order endpoint then returns status created")
    void givenValidOrder_whenCallCreateOrder_thenReturnOrderCreated() throws Exception {
        // given - setup or precondition
        ProductEntity productEntity = new ProductEntity("Manzana", 1000, BigDecimal.TEN);
        ProductEntity storedProduct = productJpaRepository.save(productEntity);

        OrderLineRequest orderLine = new OrderLineRequest(null, null, storedProduct.getId(), 50);
        OrderRequest order = new OrderRequest(null, List.of(orderLine), true, BigDecimal.valueOf(50L));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(order);

        // when - action
        ResultActions resultActions =  mockMvc.perform(post("/api/v1/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"));

        // then - verify the output
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Given an invalid order when call create order endpoint then returns status failed")
    void givenInvalidOrder_whenCallCreateOrder_thenReturnStatusFailed() throws Exception {
        // given - setup or precondition
        OrderLine orderLine = new OrderLine(null, null, 5L, 0);
        Order order = new Order(null, List.of(orderLine), true, BigDecimal.valueOf(50L));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(order);

        // when - action
        ResultActions resultActions =  mockMvc.perform(post("/api/v1/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"));

        // then - verify the output
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given an order with missing products when call create order then returns failed status")
    void givenOrderMissingProducts_whenCallCreateOrder_thenReturnStatusFailed() throws Exception {
        // given - setup or precondition
        ProductEntity productEntity = new ProductEntity("Manzana", 1000, BigDecimal.TEN);
        ProductEntity storedProduct = productJpaRepository.save(productEntity);

        OrderLineRequest orderLine = new OrderLineRequest(null, null, storedProduct.getId(), 1001);
        OrderRequest order = new OrderRequest(null, List.of(orderLine), true, BigDecimal.valueOf(50L)
        );
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(order);

        // when - action
        ResultActions resultActions =  mockMvc.perform(post("/api/v1/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"));

        // then - verify the output
        resultActions.andExpect(status().isPreconditionFailed());
        resultActions.andExpect(content().string("{\"status\":\"PRECONDITION_FAILED\",\"message\":\"Failed!\",\"body\":\"The missing products are [Product{id=1, name='Manzana', quantity=1000, price=10.00}]\"}"));
    }

    /**

    @Test
    @DisplayName("Given a valid order when call cancel order endpoint then order is canceled and product recover the quantity")
    void givenValidOrder_whenCallCancelOrder_thenOrderCanceledAndProductRecoverQuantity() throws Exception {
        // given - setup or precondition
        ProductEntity productEntity = new ProductEntity("Manzana", 1000, BigDecimal.TEN);
        ProductEntity storedProduct = productJpaRepository.save(productEntity);

        OrderLineRequest orderLine = new OrderLineRequest(null, null, storedProduct.getId(), 50);
        OrderRequest order = new OrderRequest(null, List.of(orderLine), true, BigDecimal.valueOf(50L));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(order);

        List<OrderLineEntity> orderLineEntities = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity(orderLineEntities, BigDecimal.TEN);

        OrderLineEntity orderLineEntity = new OrderLineEntity(orderEntity, productEntity, 1);
        orderLineEntities.add(orderLineEntity);

        OrderEntity savedOrder = orderJpaRepository.save(orderEntity);


        // when - action
        ResultActions deleteResult =  mockMvc.perform(delete("/api/v1/order/"+savedOrder.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"));

        // then - verify the output
       deleteResult.andExpect(status().isOk());
        Assertions.assertEquals(productEntity.getQuantity() , productJpaRepository.getProductEntityById(productEntity.getId()).get().getQuantity());
        Assertions.assertEquals(Optional.empty(), orderJpaRepository.getOrderEntityById(savedOrder.getId()));
    }

         */

}
