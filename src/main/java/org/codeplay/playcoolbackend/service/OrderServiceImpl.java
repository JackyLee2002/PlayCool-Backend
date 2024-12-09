package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.common.OrderStatus;
import org.codeplay.playcoolbackend.common.PaymentStatus;
import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;
import org.codeplay.playcoolbackend.entity.Order;
import org.codeplay.playcoolbackend.mapper.OrderMapper;
import org.codeplay.playcoolbackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

//    @Autowired
//    private ConcertRepository concertRepository;
//
//    @Autowired
//    private AreaRepository areaRepository;
//
//    @Autowired
//    private SeatRepository seatRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<OrderResponseDto> getOrdersByUserId(Integer userId) {
        List<Order> orderByUserId = orderRepository.findOrdersByUserId(userId);
        return orderByUserId.stream()
                .map(saveOrder -> {

//                    OrderResponseDto orderResponse = orderMapper.mapOrderToOrderResponseDto(saveOrder);
                    OrderResponseDto orderResponse = new OrderResponseDto();
                    orderResponse.setOrderId(saveOrder.getOrderId());
                    orderResponse.setOrderStatus(saveOrder.getOrderStatus());
                    orderResponse.setPaymentStatus(saveOrder.getPaymentStatus());
                    orderResponse.setPrice(saveOrder.getPrice());
                    orderResponse.setPaymentMethod(saveOrder.getPaymentMethod());
                    orderResponse.setCreatedAt(saveOrder.getCreatedAt());

//                    extracted(order, orderResponseDto);
                    orderResponse.setConcertName("concertName");
                    orderResponse.setConcertDate(new Date());
                    orderResponse.setAreaName("areaName");
                    orderResponse.setSeatNumber(1);
                    return orderResponse;
                })
                .collect(Collectors.toList());
    }

    private static void extracted(Order order, OrderResponseDto orderResponseDto) {
        //todo use Repository  may be use function get info by id
        // Fetch concert information
//        Concert concert = concertRepository.findById(order.getConcertId()).orElse(null);
//        if (concert != null) {
//            orderResponseDto.setConcertName(concert.getName());
//            orderResponseDto.setConcertDate(concert.getDate());
//        }
//
//        // Fetch area information
//        Area area = areaRepository.findById(order.getAreaId()).orElse(null);
//        if (area != null) {
//            orderResponseDto.setAreaName(area.getName());
//        }
//
//        // Fetch seat information
//        Seat seat = seatRepository.findById(order.getSeatId()).orElse(null);
//        if (seat != null) {
//            orderResponseDto.setSeatNumber(seat.getNumber());
//        }

        orderResponseDto.setConcertName("concertName");
        orderResponseDto.setConcertDate(new Date());
        orderResponseDto.setAreaName("areaName");
        orderResponseDto.setSeatNumber(1);
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();

        order.setUserId(orderRequestDto.getUserId());
        order.setConcertId(orderRequestDto.getConcertId());
        order.setSeatId(orderRequestDto.getSeatId());
        order.setPrice(orderRequestDto.getPrice());
        order.setPaymentMethod(orderRequestDto.getPaymentMethod());

        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setCreatedAt(new Date());
        Order saveOrder = orderRepository.save(order);

//        OrderResponseDto orderResponse = orderMapper.mapOrderToOrderResponseDto(saveOrder);
        OrderResponseDto orderResponse = new OrderResponseDto();
        orderResponse.setOrderId(saveOrder.getOrderId());
        orderResponse.setOrderStatus(saveOrder.getOrderStatus());
        orderResponse.setPaymentStatus(saveOrder.getPaymentStatus());
        orderResponse.setPrice(saveOrder.getPrice());
        orderResponse.setPaymentMethod(saveOrder.getPaymentMethod());
        orderResponse.setCreatedAt(saveOrder.getCreatedAt());


        //todo use Repository  may be use function get info by id
        orderResponse.setConcertName("concertName");
        orderResponse.setConcertDate(new Date());
        orderResponse.setAreaName("areaName");
        orderResponse.setSeatNumber(1);

        return orderResponse;
    }

    @Override
    public OrderResponseDto payOrder(PaymentRequestDto paymentRequestDto) {
        Order order = orderRepository.findById(paymentRequestDto.getOrderId()).orElseThrow(null);
        order.setPaymentStatus(PaymentStatus.COMPLETED);
        order.setOrderStatus(OrderStatus.UNUSED);
        Order saveOrder = orderRepository.save(order);

//        OrderResponseDto orderResponse = orderMapper.mapOrderToOrderResponseDto(saveOrder);
        OrderResponseDto orderResponse = new OrderResponseDto();
        orderResponse.setOrderId(saveOrder.getOrderId());
        orderResponse.setOrderStatus(saveOrder.getOrderStatus());
        orderResponse.setPaymentStatus(saveOrder.getPaymentStatus());
        orderResponse.setPrice(saveOrder.getPrice());
        orderResponse.setPaymentMethod(saveOrder.getPaymentMethod());
        orderResponse.setCreatedAt(saveOrder.getCreatedAt());

//        extracted(saveOrder, orderResponse);
        orderResponse.setConcertName("concertName");
        orderResponse.setConcertDate(new Date());
        orderResponse.setAreaName("areaName");
        orderResponse.setSeatNumber(1);

        return orderResponse;
    }

    @Override
    public OrderResponseDto useOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(null);
        order.setOrderStatus(OrderStatus.COMPLETED);
        Order saveOrder = orderRepository.save(order);

//        OrderResponseDto orderResponse = orderMapper.mapOrderToOrderResponseDto(saveOrder);
        OrderResponseDto orderResponse = new OrderResponseDto();
        orderResponse.setOrderId(saveOrder.getOrderId());
        orderResponse.setOrderStatus(saveOrder.getOrderStatus());
        orderResponse.setPaymentStatus(saveOrder.getPaymentStatus());
        orderResponse.setPrice(saveOrder.getPrice());
        orderResponse.setPaymentMethod(saveOrder.getPaymentMethod());
        orderResponse.setCreatedAt(saveOrder.getCreatedAt());

        //        extracted(saveOrder, orderResponse);
        orderResponse.setConcertName("concertName");
        orderResponse.setConcertDate(new Date());
        orderResponse.setAreaName("areaName");
        orderResponse.setSeatNumber(1);

        return orderResponse;
    }
}
