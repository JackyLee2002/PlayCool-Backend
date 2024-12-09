package org.codeplay.playcoolbackend.controller;



import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.dto.VoteDto;
import org.codeplay.playcoolbackend.service.SongService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
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
    void testGetAllSongs() throws Exception {
        List<SongDto> mockSongs = Arrays.asList(
                new SongDto(1L, "Song A", "Album A", "2023-01-01", 10L),
                new SongDto(2L, "Song B", "Album B", "2023-01-02", 20L)
        );

        when(songService.getAll()).thenReturn(mockSongs);

        mockMvc.perform(get("/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Song A\",\"album\":\"Album A\",\"releaseDate\":\"2023-01-01\",\"votes\":10},{\"id\":2,\"name\":\"Song B\",\"album\":\"Album B\",\"releaseDate\":\"2023-01-02\",\"votes\":20}]"));
    }

    @Test
    void testIsVoted() throws Exception {
        Long userId = 1L;
        when(songService.isVoted(userId)).thenReturn(true);

        mockMvc.perform(get("/songs/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testVote() throws Exception {
        VoteDto voteDto = new VoteDto();
        voteDto.setUserId(1L);
        voteDto.setSongId(1L);

        doNothing().when(songService).vote(voteDto);

        mockMvc.perform(post("/songs/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"songId\":1}"))
                .andExpect(status().isOk());
    }

}