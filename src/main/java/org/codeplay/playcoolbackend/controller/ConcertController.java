package org.codeplay.playcoolbackend.controller;

import org.codeplay.playcoolbackend.entity.Concert;
import org.codeplay.playcoolbackend.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @Autowired
    private ConcertService concertService;

    @GetMapping
    public ResponseEntity<List<Concert>> getAllConcerts() {
        List<Concert> concerts = concertService.getAllConcerts();
        return ResponseEntity.ok(concerts);
    }

    @GetMapping("/coming")
    public ResponseEntity<List<Concert>> getComingConcerts() {
        List<Concert> concerts = concertService.getComingConcerts();
        return ResponseEntity.ok(concerts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Concert> getConcertById(@PathVariable Long id) {
        return ResponseEntity.ok(concertService.getConcertById(id));
    }
}
