package com.bilalyesfi.store.entrypoint.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class OrderRequest {

    Long id;

    @Valid
    @NotNull(message = "OrderLines can't be null")
    List<OrderLineRequest> orderLines;

    Boolean isPayed;

    BigDecimal totalCost;

    public OrderRequest(Long id, List<OrderLineRequest> orderLines, Boolean isPayed, BigDecimal totalCost) {
        this.id = id;
        this.orderLines = orderLines;
        this.isPayed = isPayed;
        this.totalCost = totalCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderLineRequest> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineRequest> orderLines) {
        this.orderLines = orderLines;
    }

    public Boolean getIsPayed() {
        return isPayed;
    }

    public void setIsPayed(Boolean payed) {
        isPayed = payed;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}