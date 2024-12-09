package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;
import org.codeplay.playcoolbackend.entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> getOrdersByUserId(Long userId);

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto payOrder(PaymentRequestDto paymentRequestDto);

    OrderResponseDto useOrder(Long orderId);

    OrderResponseDto getOrderById(Long orderId);

    OrderResponseDto snapOrder(Long orderId);

    Order getOrder(Long orderId);
}
