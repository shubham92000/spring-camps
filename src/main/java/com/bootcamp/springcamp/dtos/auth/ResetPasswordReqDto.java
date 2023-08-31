package com.bootcamp.springcamp.dtos.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPasswordReqDto {
    private String password;
}
