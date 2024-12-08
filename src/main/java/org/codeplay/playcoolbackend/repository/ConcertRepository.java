package org.codeplay.playcoolbackend.repository;

import org.codeplay.playcoolbackend.dto.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByFinishedFalse();
}