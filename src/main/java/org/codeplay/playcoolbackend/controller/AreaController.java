package org.codeplay.playcoolbackend.controller;

import org.codeplay.playcoolbackend.dto.AvailableSeatsCountDto;
import org.codeplay.playcoolbackend.entity.Area;
import org.codeplay.playcoolbackend.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    public ResponseEntity<List<Area>> getAllAreas() {
        List<Area> areas = areaService.getAllAreas();
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Area> getAreaById(@PathVariable Long id) {
        return ResponseEntity.ok(areaService.getAreaById(id));
    }

    @GetMapping("/venue/{id}")
    public ResponseEntity<List<Area>> getAllAreas(@PathVariable Long id) {
        return ResponseEntity.ok(areaService.getAreaByVenueId(id));
    }

    @GetMapping("/availableSeats/{venueId}")
    public ResponseEntity<List<AvailableSeatsCountDto>> getAvailableSeatsCountByVenueId(@PathVariable Long venueId) {
        List<AvailableSeatsCountDto> availableSeatsCount = areaService.getAvailableSeatsCountByVenueId(venueId);
        return ResponseEntity.ok(availableSeatsCount);
    }
}
