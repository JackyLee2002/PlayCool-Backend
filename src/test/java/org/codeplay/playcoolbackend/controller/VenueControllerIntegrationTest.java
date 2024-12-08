package org.codeplay.playcoolbackend.controller;

import org.codeplay.playcoolbackend.dto.Venue;
import org.codeplay.playcoolbackend.dto.Concert;
import org.codeplay.playcoolbackend.service.VenueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VenueControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VenueService venueService;

    @Test
    public void testGetAllVenues() throws Exception {
        Venue venue1 = new Venue();
        Venue venue2 = new Venue();
        when(venueService.getAllVenues()).thenReturn(Arrays.asList(venue1, venue2));

        mockMvc.perform(get("/venues")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));
    }

    @Test
    public void testGetVenueById() throws Exception {
        Venue venue = new Venue();
        when(venueService.getVenueById(1L)).thenReturn(Optional.of(venue));

        mockMvc.perform(get("/venues/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void testGetConcertsByVenueId() throws Exception {
        Concert concert1 = new Concert();
        Concert concert2 = new Concert();
        when(venueService.getConcertsByVenueId(1L)).thenReturn(Arrays.asList(concert1, concert2));

        mockMvc.perform(get("/venues/1/concerts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));
    }
}