package org.codeplay.playcoolbackend.mapper;

import org.codeplay.playcoolbackend.dto.VoteDto;
import org.codeplay.playcoolbackend.entity.Vote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    VoteDto toDto(Vote entity);

    Vote toPo(VoteDto dto);
}
