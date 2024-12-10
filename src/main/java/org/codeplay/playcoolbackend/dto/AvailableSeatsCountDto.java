package org.codeplay.playcoolbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvailableSeatsCountDto {
    private Long areaId;

    private String areaName;

    private Double price;

    private Long availableSeatsCount;
}
