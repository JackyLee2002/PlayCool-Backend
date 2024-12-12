package org.codeplay.playcoolbackend.repository;

import org.codeplay.playcoolbackend.dto.SaleStatsDto;
import org.codeplay.playcoolbackend.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findOrdersByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    @Query(value="SELECT new org.codeplay.playcoolbackend.dto.SaleStatsDto(c.title , c.dateTime, a.name, COUNT(s.seatId), SUM(o.price)) " +
            "FROM " +
            "    orders o " +
            "left JOIN " +
            "    Concert c ON o.concertId = c.concertId " +
            "left JOIN " +
            "    Area a ON o.areaId = a.areaId " +
            "left JOIN " +
            "    Seat s ON o.seatId = s.seatId " +
            "WHERE " +
            "    o.paymentStatus = 1 and s.status = 'Sold' " +
            "GROUP BY " +
            "    c.title, c.dateTime, a.name " +
            "ORDER BY " +
            "    c.dateTime, c.title, a.name")
    List<SaleStatsDto> getSaleStats();
}
