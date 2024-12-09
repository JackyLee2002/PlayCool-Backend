package org.codeplay.playcoolbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codeplay.playcoolbackend.common.OrderStatus;
import org.codeplay.playcoolbackend.common.PaymentStatus;
import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.dto.PaymentRequestDto;
import org.codeplay.playcoolbackend.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    public void given_useId_when_test_getAllOrders_byUserId_then_list_orderResponseDto() throws Exception {
        OrderResponseDto order = new OrderResponseDto();
        order.setOrderId(1);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setPrice(100);
        order.setPaymentMethod("paymentMethod");
        order.setCreatedAt(new Date());

        when(orderService.getOrdersByUserId(1)).thenReturn(List.of(order));
        mockMvc.perform(get("/order/getAll/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenOrderRequestDto_whenCreateOrder_thenOrderResponseDto() throws Exception {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setUserId(1);
        orderRequestDto.setConcertId(1);
        orderRequestDto.setPrice(100);
        orderRequestDto.setPaymentMethod("paymentMethod");

        OrderResponseDto order = new OrderResponseDto();
        order.setOrderId(1);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setPrice(100);
        order.setPaymentMethod("paymentMethod");
        order.setCreatedAt(new Date());

        when(orderService.createOrder(orderRequestDto)).thenReturn(order);
        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void givenPaymentRequestDto_whenPayOrder_thenOrderResponseDto() throws Exception {
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setOrderId(1);
        paymentRequestDto.setPaymentMethod("paymentMethod");

        OrderResponseDto order = new OrderResponseDto();
        order.setOrderId(1);
        order.setOrderStatus(OrderStatus.UNUSED);
        order.setPaymentStatus(PaymentStatus.COMPLETED);
        order.setPrice(100);
        order.setPaymentMethod("paymentMethod");
        order.setCreatedAt(new Date());

        when(orderService.payOrder(paymentRequestDto)).thenReturn(order);
        mockMvc.perform(put("/order/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(paymentRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenOrderId_whenUseOrder_thenOrderResponseDto() throws Exception {
        OrderResponseDto order = new OrderResponseDto();
        order.setOrderId(1);
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setPaymentStatus(PaymentStatus.COMPLETED);
        order.setPrice(100);
        order.setPaymentMethod("paymentMethod");
        order.setCreatedAt(new Date());

        when(orderService.useOrder(1)).thenReturn(order);
        mockMvc.perform(put("/order/use/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


}
