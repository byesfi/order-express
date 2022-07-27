package com.bilalyesfi.store.entrypoint.job;

import com.bilalyesfi.store.domain.usecase.order.CancelOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderCleaner {

    private static final Logger log = LoggerFactory.getLogger(OrderCleaner.class);

    private final CancelOrderCommand cancelOrderCommand;

    public OrderCleaner(CancelOrderCommand cancelOrderCommand) {
        this.cancelOrderCommand = cancelOrderCommand;
    }

    @Value("${time.to.delete:30}")
    private int timeToDelete;

    @Scheduled(fixedRate = 300000)
    @Async
    public void clean() {
        log.info("Order cleaner started");
        cancelOrderCommand.removeNotPayedOrder(LocalDateTime.now().minusMinutes(timeToDelete));
        log.info("Order cleaner finished");
    }
}
