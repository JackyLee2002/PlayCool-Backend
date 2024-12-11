package org.codeplay.playcoolbackend.repository;

import org.codeplay.playcoolbackend.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findOrdersByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
