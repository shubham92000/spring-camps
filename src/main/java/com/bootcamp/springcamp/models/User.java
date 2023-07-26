package com.bootcamp.springcamp.models;

import com.bootcamp.springcamp.utils.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "users")
public class User {
    @Id
    private String id;
    public String name;
    @Indexed(unique = true)
    public String email;
    public List<Role> roles;
    public String password;
    public String resetPasswordToken;
    public LocalDateTime resetPasswordExpire;
    public LocalDateTime createdAt;

    public User(String name, String email, List<Role> roles, String password, String resetPasswordToken, LocalDateTime resetPasswordExpire, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.resetPasswordToken = resetPasswordToken;
        this.resetPasswordExpire = resetPasswordExpire;
        this.createdAt = createdAt;
    }
}
