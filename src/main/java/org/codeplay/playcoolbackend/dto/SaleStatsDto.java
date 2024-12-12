package org.codeplay.playcoolbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleStatsDto {
    private String concertName;
    private Date concertDate;
    private String areaName;
    private Long soldSeats;
    private Double totalRevenue;
}
