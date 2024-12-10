package org.codeplay.playcoolbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;
import org.codeplay.playcoolbackend.entity.Order;
import org.codeplay.playcoolbackend.entity.User;
import org.codeplay.playcoolbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@AuthenticationPrincipal User user, @RequestBody OrderRequestDto orderRequestDto) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        orderRequestDto.setUserId(user.getId());
        OrderResponseDto temporaryOrder = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(temporaryOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        return ResponseEntity.ok(orderService.getOrdersByUserId(user.getId()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@AuthenticationPrincipal User user, @PathVariable Long orderId) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PutMapping("/pay")
    public ResponseEntity<OrderResponseDto> payOrder(@AuthenticationPrincipal User user, @RequestBody PaymentRequestDto paymentRequestDto) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        Order order = orderService.getOrder(paymentRequestDto.getOrderId());
        if (!Objects.equals(order.getUserId(), user.getId())) {
            throw new IllegalArgumentException("You are not allowed to pay this order");
        }
        OrderResponseDto orderResponseDto = orderService.payOrder(paymentRequestDto);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/show/{orderId}")
    public ResponseEntity<OrderResponseDto> showOrder(@AuthenticationPrincipal User user, @PathVariable Long orderId) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        Order order = orderService.getOrder(orderId);
        if (!Objects.equals(order.getUserId(), user.getId())) {
            throw new IllegalArgumentException("You are not allowed to use this order");
        }
        OrderResponseDto orderResponseDto = orderService.useOrder(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/snap/{orderId}")
    public ResponseEntity<OrderResponseDto> snapOrder(@AuthenticationPrincipal User user, @PathVariable Long orderId) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        Order order = orderService.getOrder(orderId);
        if (!Objects.equals(order.getUserId(), user.getId())) {
            throw new IllegalArgumentException("You are not allowed to snap this order");
        }
        OrderResponseDto orderResponseDto = orderService.snapOrder(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }
}