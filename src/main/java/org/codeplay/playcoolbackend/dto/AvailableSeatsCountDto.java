package org.codeplay.playcoolbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvailableSeatsCountDto {
    private String areaName;

    private Long availableSeatsCount;
}
