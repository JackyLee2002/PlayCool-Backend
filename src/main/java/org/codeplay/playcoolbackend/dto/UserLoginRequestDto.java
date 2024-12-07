package org.codeplay.playcoolbackend.dto;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String username;
    private String password;
}
