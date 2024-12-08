package org.codeplay.playcoolbackend.repository;

import org.codeplay.playcoolbackend.dto.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByFinishedFalse();
//    write a query that returns all concert in a specific venue given the venue id, please note that in the concert entity the venue is a ManyToOne relationship and its not the id
    @Query("SELECT c FROM Concert c WHERE c.venue.id = :venueId")
    List<Concert> findByVenue(Long venueId);
}