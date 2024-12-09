package org.codeplay.playcoolbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponseDto {
    private Integer orderId;
    private String userName;
    private String concertName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date concertDate;
    private String AreaName;
    private Integer seatNumber;
    private Integer price;
}
