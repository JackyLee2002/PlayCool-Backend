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
    private Long userId;
    private Long concertId;
    private Long venueId;
    private Long areaId;
    private String paymentMethod;
}