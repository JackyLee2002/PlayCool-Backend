package org.codeplay.playcoolbackend.repository;

import org.codeplay.playcoolbackend.dto.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
}