package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.AvailableSeatsCountDto;
import org.codeplay.playcoolbackend.entity.Area;
import org.codeplay.playcoolbackend.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    public List<Area> getAllAreas() {
        return areaRepository.findAll();
    }

    public Optional<Area> getAreaById(Long id) {
        return areaRepository.findById(id);
    }

    public List<Area> getAreaByVenueId(Long id) {
        return areaRepository.findByVenue(id);
    }

    public List<AvailableSeatsCountDto> getAvailableSeatsCountByVenueId(Long venueId) {
        return areaRepository.findAvailableSeatsCountByVenueId(venueId);
    }
}
