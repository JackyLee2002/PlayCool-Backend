package org.codeplay.playcoolbackend.repository;

import org.codeplay.playcoolbackend.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findOrdersByUserId(Long userId, Pageable pageable);
}
