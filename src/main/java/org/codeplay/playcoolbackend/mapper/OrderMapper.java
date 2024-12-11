package org.codeplay.playcoolbackend.mapper;

import org.codeplay.playcoolbackend.dto.OrderRequestDto;
import org.codeplay.playcoolbackend.dto.OrderResponseDto;
import org.codeplay.playcoolbackend.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order mapOrderRequestDtoToOrder(OrderRequestDto orderRequestDto);

    OrderRequestDto mapOrderToOrderRequestDto(Order order);

    OrderResponseDto mapOrderToOrderResponseDto(Order order);

    OrderResponseDto mapOrderRequestDtoToOrderResponseDto(OrderRequestDto orderRequestDto);
}
