package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;
import org.codeplay.playcoolbackend.dto.SaleStatsDto;
import org.codeplay.playcoolbackend.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrderService {
    Page<OrderResponseDto> getOrdersByUserId(Long userId, Pageable pageable);

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto payOrder(PaymentRequestDto paymentRequestDto);

    OrderResponseDto useOrder(Long orderId);

    OrderResponseDto getOrderById(Long orderId);

    OrderResponseDto snapOrder(Long orderId);

    Order getOrder(Long orderId);

    List<SaleStatsDto> getAllOrders();
}
