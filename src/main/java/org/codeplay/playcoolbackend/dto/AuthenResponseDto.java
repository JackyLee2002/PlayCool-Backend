package org.codeplay.playcoolbackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenResponseDto {
    private String token;
}
