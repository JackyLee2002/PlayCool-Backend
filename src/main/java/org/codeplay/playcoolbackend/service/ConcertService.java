package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.Concert;
import org.codeplay.playcoolbackend.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    public List<Concert> getComingConcerts() {
        return concertRepository.findByFinishedFalse();
    }

    public Optional<Concert> getConcertById(Long id) {
        return concertRepository.findById(id);
    }
}