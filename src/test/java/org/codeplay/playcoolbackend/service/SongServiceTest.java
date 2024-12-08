package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.entity.Song;
import org.codeplay.playcoolbackend.mapper.SongMapper;
import org.codeplay.playcoolbackend.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongService songService;

    @Spy
    private SongMapper songMapper = Mappers.getMapper(SongMapper.class);

    @Test
    public void should_return_all_songs_when_getAll() {
        // given
        Song song = new Song();
        song.setId(1L);
        song.setName("song1");
        song.setVotes(1L);
        when(songRepository.findAll()).thenReturn(List.of(song));

        // when
        List<SongDto> result = songService.getAll();

        // then
        assertEquals(1, result.size());
        assertEquals("song1", result.get(0).getName());
        assertEquals(1L, result.get(0).getVotes());
    }

    @Test
    public void should_vote_success_when_vote() {
        // given
        Song song = new Song();
        song.setId(1L);
        song.setName("song1");
        song.setVotes(1L);
        when(songRepository.findById(1L)).thenReturn(java.util.Optional.of(song));

        // when
        songService.vote(1L);

        // then
        verify(songRepository, times(1)).save(song);
        assertEquals(2L, song.getVotes());
    }
}