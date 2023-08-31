package com.bootcamp.springcamp.dtos.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatePasswordReqDto {
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
