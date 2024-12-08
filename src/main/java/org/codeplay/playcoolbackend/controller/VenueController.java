package org.codeplay.playcoolbackend.controller;

import org.codeplay.playcoolbackend.dto.Venue;
import org.codeplay.playcoolbackend.dto.Concert;
import org.codeplay.playcoolbackend.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping
    public ResponseEntity<List<Venue>> getAllVenues() {
        List<Venue> venues = venueService.getAllVenues();
        return ResponseEntity.ok(venues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        return venueService.getVenueById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/concerts")
    public ResponseEntity<List<Concert>> getConcertsByVenueId(@PathVariable Long id) {
        List<Concert> concerts = venueService.getConcertsByVenueId(id);
        return ResponseEntity.ok(concerts);
    }
}