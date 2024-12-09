package org.codeplay.playcoolbackend.repository;

import org.codeplay.playcoolbackend.entity.Concert;
import org.codeplay.playcoolbackend.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findByNameContainingIgnoreCaseOrCityContainingIgnoreCase(String name, String city);

}
