package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.entity.Venue;
import org.codeplay.playcoolbackend.entity.Concert;
import org.codeplay.playcoolbackend.repository.VenueRepository;
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
public class VenueServiceTest {

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private VenueService venueService;

    @Test
    public void testGetAllVenues() {
        Venue venue1 = new Venue();
        Venue venue2 = new Venue();
        when(venueRepository.findAll()).thenReturn(Arrays.asList(venue1, venue2));

        List<Venue> venues = venueService.getAllVenues();
        assertEquals(2, venues.size());
    }

    @Test
    public void testGetVenueById() {
        Venue venue = new Venue();
        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));

        Venue foundVenue = venueService.getVenueById(1L);
        assertTrue(foundVenue != null);
        assertEquals(venue, foundVenue);
    }

    @Test
    public void testGetConcertsByVenueId() {
        Concert concert1 = new Concert();
        Concert concert2 = new Concert();
        when(concertRepository.findByVenue(1L)).thenReturn(Arrays.asList(concert1, concert2));

        List<Concert> concerts = venueService.getConcertsByVenueId(1L);
        assertEquals(2, concerts.size());
    }
}
