package org.codeplay.playcoolbackend.service;

import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.dto.VoteDto;

import java.util.List;

public interface SongService {
    List<SongDto> getAll();

    void vote(VoteDto voteDto);

    Boolean canVote(Long userId);

    List<Long> getVotedSongId(Long userId);

    Long getVotesByUserId(Long songId);

    Long getAllVotes();
}
