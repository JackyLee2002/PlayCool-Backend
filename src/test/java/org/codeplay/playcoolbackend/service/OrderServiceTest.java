package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.common.OrderStatus;
import org.codeplay.playcoolbackend.common.PaymentStatus;
import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;
import org.codeplay.playcoolbackend.entity.Order;
import org.codeplay.playcoolbackend.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    public OrderServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenUserId_whenGetAllOrdersByUserId_thenListOrderResponseDto() {
        Order order = new Order();
        order.setOrderId(1);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setPrice(100);
        order.setPaymentMethod("paymentMethod");
        order.setCreatedAt(new Date());

        when(orderRepository.findOrdersByUserId(1)).thenReturn(List.of(order));

        List<OrderResponseDto> orders = orderService.getOrdersByUserId(1);
        assertEquals(1, orders.size());
        assertEquals(OrderStatus.PENDING, orders.get(0).getOrderStatus());
    }

    @Test
    public void givenOrderRequestDto_whenCreateOrder_thenOrderResponseDto() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setUserId(1);
        orderRequestDto.setConcertId(1);
        orderRequestDto.setPrice(100);
        orderRequestDto.setPaymentMethod("paymentMethod");

        Order order = new Order();
        order.setOrderId(1);
        order.setUserId(orderRequestDto.getUserId());
        order.setConcertId(orderRequestDto.getConcertId());
        order.setPrice(orderRequestDto.getPrice());
        order.setPaymentMethod(orderRequestDto.getPaymentMethod());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setCreatedAt(new Date());

        when(orderRepository.save(any())).thenReturn(order);

        OrderResponseDto orderResponse = orderService.createOrder(orderRequestDto);
        assertEquals(OrderStatus.PENDING, orderResponse.getOrderStatus());
        assertEquals(PaymentStatus.PENDING, orderResponse.getPaymentStatus());
        assertEquals(orderRequestDto.getPrice(), orderResponse.getPrice());
        assertEquals(orderRequestDto.getPaymentMethod(), orderResponse.getPaymentMethod());
    }

    @Test
    public void givenPaymentRequestDto_whenPayOrder_thenOrderResponseDto() {
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setOrderId(1);
        paymentRequestDto.setPaymentMethod("paymentMethod");

        Order order = new Order();
        order.setOrderId(1);
        order.setOrderStatus(OrderStatus.UNUSED);
        order.setPaymentStatus(PaymentStatus.COMPLETED);
        order.setPrice(100);
        order.setPaymentMethod("paymentMethod");
        order.setCreatedAt(new Date());

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        OrderResponseDto orderResponse = orderService.payOrder(paymentRequestDto);
        assertEquals(OrderStatus.UNUSED, orderResponse.getOrderStatus());
    }

    @Test
    public void givenOrderId_whenUseOrder_thenOrderResponseDto() {
        Order order = new Order();
        order.setOrderId(1);
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setPaymentStatus(PaymentStatus.COMPLETED);
        order.setPrice(100);
        order.setPaymentMethod("paymentMethod");
        order.setCreatedAt(new Date());

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        OrderResponseDto orderResponse = orderService.useOrder(1);
        assertEquals(OrderStatus.COMPLETED, orderResponse.getOrderStatus());
    }
}