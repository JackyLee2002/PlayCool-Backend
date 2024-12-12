package org.codeplay.playcoolbackend.controller;

import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.dto.VoteDto;
import org.codeplay.playcoolbackend.entity.User;
import org.codeplay.playcoolbackend.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/can-vote")
    public ResponseEntity<Boolean> canVote(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new IllegalStateException("User not logged in");
        }
        return ResponseEntity.ok(songService.canVote(user.getId()));
    }


    @PostMapping("/vote")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> vote(@AuthenticationPrincipal User user, @RequestBody VoteDto voteDto) {
        if (user == null) {
            throw new IllegalStateException("User not logged in");
        }
        voteDto.setUserId(user.getId());
        songService.vote(voteDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/voted-song")
    public ResponseEntity<List<Long>> getVotedSongId(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new IllegalStateException("User not logged in");
        }
        return ResponseEntity.ok(songService.getVotedSongId(user.getId()));
    }

    @GetMapping("/votes")
    public ResponseEntity<Long> getVotesByUserId(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new IllegalStateException("User not logged in");
        }
        return ResponseEntity.ok(songService.getVotesByUserId(user.getId()));
    }

    @GetMapping("/all-votes")
    public ResponseEntity<Long> getAllVotes() {
        return ResponseEntity.ok(songService.getAllVotes());
    }

    @PostMapping("/mock")
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
        voteDto.setUserId((long) -1);
        songService.vote(voteDto);
        return ResponseEntity.ok().build();
    }

}
