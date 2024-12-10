package org.codeplay.playcoolbackend.controller;

import org.codeplay.playcoolbackend.entity.Venue;
import org.codeplay.playcoolbackend.entity.Concert;
import org.codeplay.playcoolbackend.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(venueService.getVenueById(id));
    }

    @GetMapping("/{id}/concerts")
    public ResponseEntity<List<Concert>> getConcertsByVenueId(@PathVariable Long id) {
        return ResponseEntity.ok(venueService.getConcertsByVenueId(id));
    }

    @GetMapping("/concerts")
    public ResponseEntity<List<Concert>> getConcertsByKeyword(@RequestParam String Keyword) {
        return ResponseEntity.ok(venueService.getConcertByKeyword(Keyword));
    }
}
