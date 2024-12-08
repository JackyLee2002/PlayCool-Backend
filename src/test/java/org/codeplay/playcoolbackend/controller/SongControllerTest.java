package org.codeplay.playcoolbackend.controller;



import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.service.SongService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class SongControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;

    @Test
    public void should_get_songs_when_getAll_given_data() throws Exception {
        // given
        SongDto songDto = SongDto.builder()
                .name("song1")
                .votes(1L)
                .build();
        when(songService.getAll()).thenReturn(List.of(songDto));

        // when
        mockMvc.perform(get("/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"song1\",\"votes\":1}]"));
    }

    @Test
    public void should_vote_success_when_vote() throws Exception {
        // given
        Long id = 1L;
        doNothing().when(songService).vote(id);

        // when
        mockMvc.perform(post("/songs/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk());
    }

}