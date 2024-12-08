package org.codeplay.playcoolbackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.codeplay.playcoolbackend.common.OrderStatus;

import java.util.Date;

@Entity(name = "orders")
@Table(name = "orders")
@Data
public class Orders {
    @Id
    private Integer order_id;
    private Integer user_id;
    private Integer seat_id;
    private Integer concert_id;
    private OrderStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;
}
