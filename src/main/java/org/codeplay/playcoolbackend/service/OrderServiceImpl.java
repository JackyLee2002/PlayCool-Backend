package org.codeplay.playcoolbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.codeplay.playcoolbackend.common.OrderStatus;
import org.codeplay.playcoolbackend.common.PaymentStatus;
import org.codeplay.playcoolbackend.common.SeatStatus;
import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;
import org.codeplay.playcoolbackend.entity.Concert;
import org.codeplay.playcoolbackend.entity.Order;
import org.codeplay.playcoolbackend.entity.Seat;
import org.codeplay.playcoolbackend.repository.AreaRepository;
import org.codeplay.playcoolbackend.repository.ConcertRepository;
import org.codeplay.playcoolbackend.repository.OrderRepository;
import org.codeplay.playcoolbackend.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public Page<OrderResponseDto> getOrdersByUserId(Long userId, Pageable pageable) {
        Page<Order> orderByUserId = orderRepository.findOrdersByUserId(userId, pageable);
        return orderByUserId.map(saveOrder -> {
            OrderResponseDto orderResponse = getOrderResponseDto(saveOrder);
            return extractedMethod(saveOrder, orderResponse);
        });
    }

    private static OrderResponseDto getOrderResponseDto(Order saveOrder) {
        return OrderResponseDto.builder()
                .orderId(saveOrder.getOrderId())
                .orderStatus(saveOrder.getOrderStatus())
                .paymentStatus(saveOrder.getPaymentStatus())
                .price(saveOrder.getPrice())
                .paymentMethod(saveOrder.getPaymentMethod())
                .createdAt(saveOrder.getCreatedAt())
                .updatedAt(saveOrder.getUpdatedAt())
                .build();
    }

    private OrderResponseDto extractedMethod(Order order, OrderResponseDto orderResponseDto) {
        Concert concert = concertRepository.findById(order.getConcertId()).orElse(null);
        if (concert != null) {
            orderResponseDto.setConcertName(concert.getTitle());
            orderResponseDto.setConcertDate(concert.getDateTime());
            orderResponseDto.setConcertImage(concert.getConcertImage());
        }

        areaRepository.findById(order.getAreaId()).ifPresent(area -> {
            orderResponseDto.setAreaName(area.getName());
            orderResponseDto.setVenueName(area.getVenue().getName());
        });
        if (order.getSeatId() != null) {
            seatRepository.findById(order.getSeatId()).ifPresent(seat -> {
                orderResponseDto.setSeatNumber(seat.getSeatNumber());
            });
        }

        return orderResponseDto;
    }


    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Order order = Order.builder()
                .userId(orderRequestDto.getUserId())
                .concertId(orderRequestDto.getConcertId())
                .areaId(orderRequestDto.getAreaId())
                .venueId(orderRequestDto.getVenueId())
                .paymentMethod(orderRequestDto.getPaymentMethod())
                .orderStatus(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        order.setPrice(areaRepository.findById(order.getAreaId()).orElseThrow(null).getPrice());

        Order saveOrder = orderRepository.save(order);
        OrderResponseDto orderResponse = getOrderResponseDto(saveOrder);

        return extractedMethod(saveOrder, orderResponse);
    }

    @Override
    public OrderResponseDto payOrder(PaymentRequestDto paymentRequestDto) {
        Order order = orderRepository.findById(paymentRequestDto.getOrderId()).orElseThrow(null);
        order.setPaymentMethod(paymentRequestDto.getPaymentMethod());
        order.setPaymentStatus(PaymentStatus.COMPLETED);
        order.setOrderStatus(OrderStatus.UNUSED);

        Order saveOrder = orderRepository.save(order);

        Seat seat = seatRepository.findById(order.getSeatId()).orElseThrow(null);
        seat.setStatus(SeatStatus.Sold);
        seatRepository.save(seat);

        OrderResponseDto orderResponse = getOrderResponseDto(saveOrder);

        return extractedMethod(saveOrder, orderResponse);
    }

    @Override
    public OrderResponseDto useOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(null);
        order.setOrderStatus(OrderStatus.COMPLETED);
        Order saveOrder = orderRepository.save(order);

        OrderResponseDto orderResponse = getOrderResponseDto(saveOrder);

        return extractedMethod(saveOrder, orderResponse);
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(null);
        OrderResponseDto orderResponseDto = getOrderResponseDto(order);

        return extractedMethod(order, orderResponseDto);
    }

    @Override
    public OrderResponseDto snapOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(null);
        order.setPaymentStatus(PaymentStatus.NONPAYMENT);

        // need seatId and the seatId status is Available
        List<Seat> availableSeats = seatRepository.findAll().stream()
                .filter(seat -> seat.getArea().getAreaId().equals(order.getAreaId()))
                .filter(seat -> SeatStatus.Available.equals(seat.getStatus()))
                .toList();
        if (availableSeats.isEmpty()) {
            throw new IllegalArgumentException("No available seats");
        }
        Seat seat = availableSeats.get(new Random().nextInt(availableSeats.size()));
        seat.setStatus(SeatStatus.Locked);
        seatRepository.save(seat);

        order.setSeatId(seat.getSeatId());
        Order saveOrder = orderRepository.save(order);

        OrderResponseDto orderResponse = getOrderResponseDto(saveOrder);
        return extractedMethod(saveOrder, orderResponse);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(null);
    }
}
