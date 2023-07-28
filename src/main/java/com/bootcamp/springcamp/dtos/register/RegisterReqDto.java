package com.bootcamp.springcamp.dtos.register;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterReqDto {
    @NotEmpty(message = "please give a name")
    private String name;
    private String email;
    @Size(min = 6, message = "password length is too short")
    private String password;
    @NotEmpty(message = "please provide userType")
    private String userType;
}
