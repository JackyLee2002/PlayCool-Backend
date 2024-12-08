package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.mapper.SongMapper;
import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongMapper songMapper;

    public List<SongDto> getAll() {
        return songRepository.findAll().stream()
                .map(songMapper::toDto)
                .sorted(Comparator.comparing(SongDto::getName))
                .collect(Collectors.toList());
    }


    @Transactional
    public void vote(Long id) {
        songRepository.findById(id)
                .ifPresent(song -> {
                    song.setVotes(song.getVotes() + 1);
                    songRepository.save(song);
                });
    }
}
