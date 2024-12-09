package org.codeplay.playcoolbackend.dto;

import lombok.Data;

@Data
public class AvailableSeatsCountDto {
    private String areaName;

    private Long availableSeatsCount;

    public AvailableSeatsCountDto(String areaName, Long availableSeatsCount) {
        this.areaName = areaName;
        this.availableSeatsCount = availableSeatsCount;
    }
}
