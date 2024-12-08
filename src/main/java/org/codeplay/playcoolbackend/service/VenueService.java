package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.Venue;
import org.codeplay.playcoolbackend.dto.Concert;
import org.codeplay.playcoolbackend.repository.VenueRepository;
import org.codeplay.playcoolbackend.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private ConcertRepository concertRepository;

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Optional<Venue> getVenueById(Long id) {
        return venueRepository.findById(id);
    }

    public List<Concert> getConcertsByVenueId(Long venueId) {
        return concertRepository.findByVenue(venueId);
    }
}