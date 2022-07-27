package com.bilalyesfi.store.entrypoint.util;

import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.model.OrderLine;
import com.bilalyesfi.store.entrypoint.dto.OrderRequest;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static Order mapToDomain(OrderRequest orderRequest) {
        List<OrderLine> orderLines = new ArrayList<>();
        orderRequest.getOrderLines().stream()
                .map(ol -> new OrderLine(ol.getId(), ol.getOrderId(), ol.getProductId(), ol.getProductQuantity()))
                .forEach(orderLines::add);
        return new Order(orderRequest.getId(), orderLines, orderRequest.getIsPayed(), orderRequest.getTotalCost());
    }
}
