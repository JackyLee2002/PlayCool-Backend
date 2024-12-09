package org.codeplay.playcoolbackend.repository;

import org.codeplay.playcoolbackend.dto.AvailableSeatsCountDto;
import org.codeplay.playcoolbackend.entity.Area;
import org.codeplay.playcoolbackend.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    @Query("SELECT a FROM Area a WHERE a.venue.id = :venueId")
    List<Area> findByVenue(Long venueId);

    @Query("SELECT new org.codeplay.playcoolbackend.dto.AvailableSeatsCountDto(a.name, COUNT(s.seatId)) " +
            "FROM Area a LEFT JOIN Seat s ON a.id = s.area.id AND s.status = 'Available' " +
            "WHERE a.venue.id = :venueId " +
            "GROUP BY a.id, a.name")
    List<AvailableSeatsCountDto> findAvailableSeatsCountByVenueId(@Param("venueId") Long venueId);
}
