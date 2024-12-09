package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.VoteDto;
import org.codeplay.playcoolbackend.mapper.SongMapper;
import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.mapper.VoteMapper;
import org.codeplay.playcoolbackend.repository.SongRepository;
import org.codeplay.playcoolbackend.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private VoteMapper voteMapper;

    public List<SongDto> getAll() {
        return songRepository.findAll().stream()
                .map(song -> {
                    SongDto songDto = songMapper.toDto(song);
                    songDto.setVotes(getSongVotes(song.getId()));
                    return songDto;
                })
                .sorted(Comparator.comparing(SongDto::getVotes).reversed())
                .collect(Collectors.toList());
    }


    @Transactional
    public void vote(VoteDto voteDto) {
        voteRepository.save(voteMapper.toPo(voteDto));
    }

    @Override
    public Boolean isVoted(Long userId) {
        return voteRepository.existsByUserId(userId);
    }

    public Long getSongVotes(Long songId) {
        return voteRepository.countBySongId(songId);
    }
}
