package com.bilalyesfi.store.entrypoint;

import com.bilalyesfi.store.domain.model.Order;
import com.bilalyesfi.store.domain.usecase.order.CreateOrderCommand;
import com.bilalyesfi.store.domain.usecase.order.CancelOrderCommand;
import com.bilalyesfi.store.domain.usecase.order.PayOrderCommand;
import com.bilalyesfi.store.entrypoint.dto.OrderRequest;
import com.bilalyesfi.store.entrypoint.dto.ResponseDTO;
import com.bilalyesfi.store.entrypoint.util.ControllerHelper;
import com.bilalyesfi.store.entrypoint.util.OrderMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/order")
public class OrderApiController {

  private final CreateOrderCommand createOrderCommand;

  private final CancelOrderCommand cancelOrderCommand;

  private final PayOrderCommand payOrderCommand;

  public OrderApiController(CreateOrderCommand createOrderCommand,
      CancelOrderCommand cancelOrderCommand, PayOrderCommand payOrderCommand) {
    this.createOrderCommand = createOrderCommand;
    this.cancelOrderCommand = cancelOrderCommand;
    this.payOrderCommand = payOrderCommand;
  }

  @PostMapping("/create")
  public ResponseEntity<ResponseDTO<?>> createOrder(@RequestBody @Valid OrderRequest orderRequest,
                                                    BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ControllerHelper.getBindingErrorResponse(bindingResult);
    }

    Order createdOrder = createOrderCommand.handle(OrderMapper.mapToDomain(orderRequest));

    ResponseDTO<?> responseDTO = ResponseDTO.builder()
            .status(HttpStatus.CREATED)
            .body(createdOrder)
            .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @DeleteMapping("{orderId}")
  public ResponseEntity<ResponseDTO<?>> cancelOrder(@PathVariable Long orderId) {

    Order canceledOrder = cancelOrderCommand.handle(orderId);

    ResponseDTO<?> responseDTO = ResponseDTO.builder()
            .status(HttpStatus.OK)
            .body(canceledOrder)
            .build();

    return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
  }

  @PostMapping("{orderId}")
  public ResponseEntity<ResponseDTO<?>> payOrder(@PathVariable Long orderId) {

    Order payedOrder = payOrderCommand.handle(orderId);

    ResponseDTO<?> responseDTO = ResponseDTO.builder()
            .status(HttpStatus.OK)
            .body(payedOrder)
            .build();

    return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
  }

}
