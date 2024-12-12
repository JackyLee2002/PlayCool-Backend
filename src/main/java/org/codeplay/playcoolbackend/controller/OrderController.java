package org.codeplay.playcoolbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;
import org.codeplay.playcoolbackend.entity.Order;
import org.codeplay.playcoolbackend.entity.User;
import org.codeplay.playcoolbackend.service.OrderNotificationService;
import org.codeplay.playcoolbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderNotificationService orderNotificationService;

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
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int pageSize) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(orderService.getOrdersByUserId(user.getId(), pageable));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@AuthenticationPrincipal User user, @PathVariable Long orderId) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/oauth/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderByIdNOAUTH( @PathVariable Long orderId) {
        OrderResponseDto OrderResponseDto = orderService.getOrderById(orderId);
        orderNotificationService.notifyOrderChange(OrderResponseDto);
        return ResponseEntity.ok(OrderResponseDto);
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
        orderNotificationService.notifyOrderChange(orderResponseDto);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/snap/oauth/{orderId}")
    public ResponseEntity<OrderResponseDto> snapNoAuthOrder(@PathVariable Long orderId) {
        OrderResponseDto orderResponseDto = orderService.snapOrder(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }
}
