package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.Concert;
import org.codeplay.playcoolbackend.repository.ConcertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertService concertService;

    @Test
    public void testGetAllConcerts() {
        Concert concert1 = new Concert();
        Concert concert2 = new Concert();
        when(concertRepository.findAll()).thenReturn(Arrays.asList(concert1, concert2));

        List<Concert> concerts = concertService.getAllConcerts();
        assertEquals(2, concerts.size());
    }

    @Test
    public void testGetComingConcerts() {
        Concert concert1 = new Concert();
        Concert concert2 = new Concert();
        when(concertRepository.findByFinishedFalse()).thenReturn(Arrays.asList(concert1, concert2));

        List<Concert> concerts = concertService.getComingConcerts();
        assertEquals(2, concerts.size());
    }

    @Test
    public void testGetConcertById() {
        Concert concert = new Concert();
        when(concertRepository.findById(1L)).thenReturn(Optional.of(concert));

        Optional<Concert> foundConcert = concertService.getConcertById(1L);
        assertTrue(foundConcert.isPresent());
        assertEquals(concert, foundConcert.get());
    }
}