package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.SongDto;

import java.util.List;

public interface SongService {
    List<SongDto> getAll();

    void vote(Long id);
}
