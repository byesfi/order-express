package com.bilalyesfi.store;

import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.entrypoint.dto.ProductRequest;
import com.bilalyesfi.store.infrastructure.entity.ProductEntity;
import com.bilalyesfi.store.infrastructure.repository.jpa.ProductJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StoreApplication.class)
@AutoConfigureMockMvc
@DisplayName("Product API Controller V1 Integration Tests")
public class ProductApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    @DisplayName("Given a valid product When call create product endpoint Then return 201")
    void givenValidProduct_whenCallCreateProduct_thenReturnProductCreated() throws Exception {
        // given - setup or precondition

        ProductRequest product = new ProductRequest(1L, "Manzana", 2000, BigDecimal.valueOf(10));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(product);

        // when - action
        ResultActions resultActions =  mockMvc.perform(post("/api/v1/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"));

        // then - verify the output
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Given an invalid product When call create product endpoint Then return bad request")
    void givenNoValidProduct_whenCallCreateProduct_thenReturn() throws Exception {
        // given - setup or precondition
        Product product = new Product(1L, null, 2000, BigDecimal.valueOf(10));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(product);

        // when - action
        ResultActions resultActions =  mockMvc.perform(post("/api/v1/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"));

        // then - verify the output
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given a valid product When call update product endpoint Then return 200")
    void givenValidProduct_whenCallUpdateProduct_thenReturnProductUpdated() throws Exception {
        // given - setup or precondition

        ProductRequest product = new ProductRequest(1L, "Manzana", 2000, BigDecimal.valueOf(10));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(product);

        ProductRequest productToUpdate = new ProductRequest(1L, "Manzana", 3000, BigDecimal.valueOf(10));
        String jsonProductToUpdate = objectMapper.writeValueAsString(productToUpdate);

        // when - action
        mockMvc.perform(post("/api/v1/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"));

        ResultActions resultActions =  mockMvc.perform(put("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonProductToUpdate)
                .characterEncoding("utf-8"));

        // then - verify the output
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given an invalid product When call update product endpoint Then return 412")
    void givenNoValidProduct_whenCallUpdateProduct_thenReturnProductUpdated() throws Exception {
        // given - setup or precondition

        Product product = new Product(1L, null, 3000, BigDecimal.valueOf(10));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(product);

        // when - action

        ResultActions resultActions =  mockMvc.perform(put("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"));

        // then - verify the output
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given a valid product When call delete product endpoint Then product is deleted")
    void givenValidProduct_whenCallDeleteProduct_thenProductIsDeleted() throws Exception {
        // given - setup or precondition
        ProductEntity productEntity = new ProductEntity("Manzana", 19, BigDecimal.valueOf(97));
        ProductEntity savedProduct = productJpaRepository.save(productEntity);

        // when
        ResultActions resultActions =  mockMvc.perform(delete("/api/v1/product/"+savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));

        // then - verify the output
        resultActions.andExpect(status().isOk());
        assertEquals(Optional.empty(), productJpaRepository.getProductEntityById(1L));
    }
}


