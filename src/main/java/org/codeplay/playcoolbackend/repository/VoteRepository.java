package org.codeplay.playcoolbackend.repository;

import org.codeplay.playcoolbackend.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByUserId(Long userId);

    Long countBySongId(Long songId);

    List<Vote> findByUserId(Long userId);

    Long countByUserId(Long userId);
}
