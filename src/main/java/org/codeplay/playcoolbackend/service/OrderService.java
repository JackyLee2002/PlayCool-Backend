package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> getOrdersByUserId(Integer userId);

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto payOrder(PaymentRequestDto paymentRequestDto);

    OrderResponseDto useOrder(Integer orderId);
}
