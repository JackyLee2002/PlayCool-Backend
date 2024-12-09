package org.codeplay.playcoolbackend.controller;

import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.dto.VoteDto;
import org.codeplay.playcoolbackend.entity.User;
import org.codeplay.playcoolbackend.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/is-voted")
    public ResponseEntity<Boolean> isVoted(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(songService.isVoted(user.getId()));
    }


    @PostMapping("/vote")
    public ResponseEntity<Void> vote(@AuthenticationPrincipal User user, @RequestBody VoteDto voteDto) {
        voteDto.setUserId(user.getId());
        songService.vote(voteDto);
        return ResponseEntity.ok().build();
    }
}
