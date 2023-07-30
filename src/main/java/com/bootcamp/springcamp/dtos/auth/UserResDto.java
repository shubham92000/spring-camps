package com.bootcamp.springcamp.dtos.auth;

import com.bootcamp.springcamp.utils.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResDto {
    private String id;
    private String name;
    private String email;
    private List<Role> roles;
}
