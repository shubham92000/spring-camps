package com.bootcamp.springcamp.dtos.login;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginReqDto {
    private String email;
    private String password;
}
