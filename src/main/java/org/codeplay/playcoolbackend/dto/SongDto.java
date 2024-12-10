package org.codeplay.playcoolbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDto {
    private Long id;

    private String name;

    private String album;

    private String releaseDate;

    private Long votes;
}
