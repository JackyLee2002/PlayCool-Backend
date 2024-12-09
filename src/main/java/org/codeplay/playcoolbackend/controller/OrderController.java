package org.codeplay.playcoolbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;
import org.codeplay.playcoolbackend.entity.User;
import org.codeplay.playcoolbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> createOrder(@AuthenticationPrincipal User user, @RequestBody OrderRequestDto orderRequestDto) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        orderRequestDto.setUserId(user.getId());
        OrderResponseDto temporaryOrder = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(temporaryOrder);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        return ResponseEntity.ok(orderService.getOrdersByUserId(user.getId()));
    }

    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<OrderResponseDto> getAllOrders(@AuthenticationPrincipal User user, @PathVariable Integer orderId) {
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
        OrderResponseDto orderResponseDto = orderService.payOrder(paymentRequestDto);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/use/{orderId}")
    public ResponseEntity<OrderResponseDto> useOrder(@AuthenticationPrincipal User user, @PathVariable Integer orderId) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        OrderResponseDto orderResponseDto = orderService.useOrder(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/snap/ticket/{orderId}")
    public ResponseEntity<OrderResponseDto> snapOrder(@AuthenticationPrincipal User user, @PathVariable Integer orderId) {
        if (user == null) {
            throw new IllegalArgumentException("Please login first");
        }
        OrderResponseDto orderResponseDto = orderService.snapOrder(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }
}
