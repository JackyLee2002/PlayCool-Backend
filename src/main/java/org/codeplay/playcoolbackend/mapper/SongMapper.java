package org.codeplay.playcoolbackend.mapper;


import org.codeplay.playcoolbackend.dto.SongDto;
import org.codeplay.playcoolbackend.entity.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongMapper {

    SongDto toDto(Song song);

    Song toPo(SongDto songDto);
}
