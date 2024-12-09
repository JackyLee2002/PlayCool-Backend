package org.codeplay.playcoolbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {
    private Integer userId;
    private Integer concertId;
    private Integer seatId;
    private Integer price;
    private String paymentMethod;
}