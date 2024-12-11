package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.common.OrderStatus;
import org.codeplay.playcoolbackend.common.PaymentStatus;
import org.codeplay.playcoolbackend.common.SeatStatus;
import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;
import org.codeplay.playcoolbackend.entity.*;
import org.codeplay.playcoolbackend.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    public OrderServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenUserId_whenGetAllOrdersByUserId_thenListOrderResponseDto() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setPrice(100.0);
        order.setPaymentMethod("paymentMethod");
        order.setCreatedAt(new Date());
        Pageable pageable = Pageable.ofSize(10);

        Page<Order> orderPage = new PageImpl<>(List.of(order), pageable, 1);
        when(orderRepository.findOrdersByUserIdOrderByCreatedAtDesc(1L, pageable)).thenReturn(orderPage);

        Page<OrderResponseDto> orders = orderService.getOrdersByUserId(1L, pageable);
        assertEquals(1, orders.getTotalElements());
        assertEquals(OrderStatus.PENDING, orders.getContent().get(0).getOrderStatus());
    }

    @Test
    public void givenOrderRequestDto_whenCreateOrder_thenOrderResponseDto() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setUserId(1L);
        orderRequestDto.setConcertId(1L);
        orderRequestDto.setAreaId(1L);
        orderRequestDto.setUserId(1L);
        orderRequestDto.setPaymentMethod("paymentMethod");

        Order order = new Order();
        order.setOrderId(1L);
        order.setUserId(orderRequestDto.getUserId());
        order.setConcertId(orderRequestDto.getConcertId());
        order.setAreaId(orderRequestDto.getAreaId());
        order.setPaymentMethod(orderRequestDto.getPaymentMethod());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setCreatedAt(new Date());

        when(orderRepository.save(any())).thenReturn(order);
        when(areaRepository.findById(any())).thenReturn(Optional.of(new Area(1L, new Venue(), "test", 100.0, 1)));
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));

        OrderResponseDto orderResponse = orderService.createOrder(orderRequestDto);
        assertEquals(OrderStatus.PENDING, orderResponse.getOrderStatus());
        assertEquals(PaymentStatus.PENDING, orderResponse.getPaymentStatus());
        assertEquals(orderRequestDto.getPaymentMethod(), orderResponse.getPaymentMethod());
    }

    @Test
    public void givenPaymentRequestDto_whenPayOrder_thenOrderResponseDto() {
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setOrderId(1L);
        paymentRequestDto.setPaymentMethod("paymentMethod");

        Order order = new Order();
        order.setOrderId(1L);
        order.setSeatId(1L);
        order.setOrderStatus(OrderStatus.UNUSED);
        order.setPaymentStatus(PaymentStatus.COMPLETED);
        order.setPrice(100.0);
        order.setPaymentMethod("paymentMethod");
        order.setCreatedAt(new Date());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(seatRepository.findById(any())).thenReturn(Optional.of(new Seat(1L, new Area(), "A1", SeatStatus.Sold)));
        OrderResponseDto orderResponse = orderService.payOrder(paymentRequestDto);
        assertEquals(OrderStatus.UNUSED, orderResponse.getOrderStatus());
    }

    @Test
    public void givenOrderId_whenUseOrder_thenOrderResponseDto() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setPaymentStatus(PaymentStatus.COMPLETED);
        order.setPrice(100.0);
        order.setPaymentMethod("paymentMethod");
        order.setCreatedAt(new Date());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        OrderResponseDto orderResponse = orderService.useOrder(1L);
        assertEquals(OrderStatus.COMPLETED, orderResponse.getOrderStatus());
    }
}
