package org.codeplay.playcoolbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponse {
    private String message;
    private String email;
    private String subject;
    private String name;
    private String status;
}
