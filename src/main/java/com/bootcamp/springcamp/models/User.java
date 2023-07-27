package com.bootcamp.springcamp.models;

import com.bootcamp.springcamp.utils.Role;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotEmpty(message = "please give a name")
    private String name;
    @Indexed(unique = true)
    private String email;

    @Min(value = 1, message = "atleast one role must be provided")
    private List<Role> roles;
    private String password;
    private String resetPasswordToken;
    private LocalDateTime resetPasswordExpire;
    private LocalDateTime createdAt;

//    public User(String name, String email, List<Role> roles, String password) {
//        this.name = name;
//        this.email = email;
//        this.roles = roles;
//        this.password = password;
//        this.resetPasswordToken = null;
//        this.resetPasswordExpire = null;
//        this.createdAt = LocalDateTime.now();
//    }

    public User() {
        this.resetPasswordToken = null;
        this.resetPasswordExpire = null;
        this.createdAt = LocalDateTime.now();
    }
}
