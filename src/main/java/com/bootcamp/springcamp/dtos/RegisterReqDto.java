package com.bootcamp.springcamp.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterReqDto {
    private String name;
    private String email;
    private String password;
    private String userType;
}
