package org.codeplay.playcoolbackend.controller;

import org.codeplay.playcoolbackend.entity.Concert;
import org.codeplay.playcoolbackend.entity.Venue;
import org.codeplay.playcoolbackend.service.AreaService;
import org.codeplay.playcoolbackend.service.ConcertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ConcertControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConcertService concertService;



    @Test
    public void testGetAllConcerts() throws Exception {
        Concert concert1 = new Concert();
        Venue venue = new Venue();
        venue.setVenueId(1L);
        concert1.setVenue(venue);
        Concert concert2 = new Concert();
        Venue venue1 = new Venue();
        venue1.setVenueId(1L);
        concert2.setVenue(venue);

        when(concertService.getAllConcerts()).thenReturn(Arrays.asList(concert1, concert2));

        mockMvc.perform(get("/concerts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));
    }

    @Test
    public void testGetComingConcerts() throws Exception {
        Concert concert1 = new Concert();
        Concert concert2 = new Concert();
        when(concertService.getComingConcerts()).thenReturn(Arrays.asList(concert1, concert2));

        mockMvc.perform(get("/concerts/coming")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));
    }

    @Test
    public void testGetConcertById() throws Exception {
        Concert concert = new Concert();
        when(concertService.getConcertById(1L)).thenReturn(concert);

        mockMvc.perform(get("/concerts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }
}
