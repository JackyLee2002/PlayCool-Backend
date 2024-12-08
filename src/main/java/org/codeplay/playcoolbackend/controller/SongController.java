package org.codeplay.playcoolbackend.controller;

import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.service.SongService;
import org.codeplay.playcoolbackend.service.SongServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private SongService songService;

    @GetMapping
    public ResponseEntity<List<SongDto>> getAll() {
        return ResponseEntity.ok(songService.getAll());
    }

    @PostMapping("/vote")
    public ResponseEntity<Void> vote(@RequestBody SongDto songDto) {
        songService.vote(songDto.getId());
        return ResponseEntity.ok().build();
    }
}
