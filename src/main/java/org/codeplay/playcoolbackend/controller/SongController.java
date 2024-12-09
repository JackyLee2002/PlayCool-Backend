package org.codeplay.playcoolbackend.controller;

import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.dto.VoteDto;
import org.codeplay.playcoolbackend.service.SongService;
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

    @GetMapping("/{userId}")
    public ResponseEntity<Boolean> isVoted(@PathVariable Long userId) {
        return ResponseEntity.ok(songService.isVoted(userId));
    }


    @PostMapping("/vote")
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
        songService.vote(voteDto);
        return ResponseEntity.ok().build();
    }
}
