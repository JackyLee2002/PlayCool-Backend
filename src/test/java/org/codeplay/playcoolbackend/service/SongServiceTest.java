package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.dto.VoteDto;
import org.codeplay.playcoolbackend.entity.Song;
import org.codeplay.playcoolbackend.entity.Vote;
import org.codeplay.playcoolbackend.mapper.SongMapper;
import org.codeplay.playcoolbackend.mapper.VoteMapper;
import org.codeplay.playcoolbackend.repository.SongRepository;
import org.codeplay.playcoolbackend.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    @Mock
    private SongRepository songRepository;

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private SongServiceImpl songService;

    @Spy
    private SongMapper songMapper = Mappers.getMapper(SongMapper.class);

    @Spy
    private VoteMapper voteMapper = Mappers.getMapper(VoteMapper.class);

    @Test
    void testGetAll() {
        List<Song> mockSongs = Arrays.asList(
                songMapper.toPo(new SongDto(1L, "Song A", "Album A", "2023-01-01", 1L)),
                songMapper.toPo(new SongDto(2L, "Song B", "Album B", "2023-01-01", 2L))
        );

        when(songRepository.findAll()).thenReturn(mockSongs);

        List<SongDto> result = songService.getAll();

        assertEquals(2, result.size());
        assertEquals("Song A", result.get(0).getName());
        assertEquals("Song B", result.get(1).getName());
    }

    @Test
    void testIsVoted() {
        Long userId = 1L;
        when(voteRepository.existsByUserId(userId)).thenReturn(true);

        boolean result = songService.canVote(userId);

        assertEquals(true, result);
    }

    @Test
    void testVote() {
        VoteDto voteDto = new VoteDto(1L, 1L);
        Vote vote = new Vote();
        vote.setUserId(voteDto.getUserId());
        vote.setSongId(voteDto.getSongId());

        when(voteRepository.save(any(Vote.class))).thenReturn(vote);

        songService.vote(voteDto);

        verify(voteRepository, times(1)).save(any(Vote.class));
    }

}