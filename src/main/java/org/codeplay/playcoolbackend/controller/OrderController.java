package org.codeplay.playcoolbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;
import org.codeplay.playcoolbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 简单流程：
     * 生成订单
     * 到时支付
     */
    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto temporaryOrder = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(temporaryOrder);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(@PathVariable Integer userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @PutMapping("/pay")
    public ResponseEntity<OrderResponseDto> payOrder(@RequestBody PaymentRequestDto paymentRequestDto) {
        OrderResponseDto orderResponseDto = orderService.payOrder(paymentRequestDto);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/use/{orderId}")
    public ResponseEntity<OrderResponseDto> useOrder(@PathVariable Integer orderId) {
        OrderResponseDto orderResponseDto = orderService.useOrder(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }
}
