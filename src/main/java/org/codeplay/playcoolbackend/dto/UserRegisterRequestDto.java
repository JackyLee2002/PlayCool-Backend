package org.codeplay.playcoolbackend.dto;

import lombok.Data;

@Data
public class UserRegisterRequestDto {
    private String username;

    private String password;
}
