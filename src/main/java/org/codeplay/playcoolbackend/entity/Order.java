package org.codeplay.playcoolbackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codeplay.playcoolbackend.common.OrderStatus;
import org.codeplay.playcoolbackend.common.PaymentStatus;

import java.util.Date;

@Entity(name = "orders")
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long orderId;
    private Long userId;
    private Long seatId;
    private Long concertId;
    private OrderStatus orderStatus;
    private Integer price;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

}
