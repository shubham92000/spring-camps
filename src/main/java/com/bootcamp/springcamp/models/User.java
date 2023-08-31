package com.bootcamp.springcamp.models;

import com.bootcamp.springcamp.utils.Role;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.bootcamp.springcamp.services.impl.AuthServiceImpl.RESET_PASSWORD_TOKEN_EXPIRY;

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

    @Size(min = 6, message = "password length is too short")
    private String password;
    private String resetPasswordToken;
    private LocalDateTime resetPasswordExpire;

    @CreatedDate
    private LocalDateTime createdOn;

    @LastModifiedDate
    private LocalDateTime lastModifiedOn;

    public User() {
        this.resetPasswordToken = null;
        this.resetPasswordExpire = null;
    }

    @Transient
    public String generateResetPasswordToken(){
        String resetToken = UUID.randomUUID().toString();;

        // can hash the reset token with any hashing algo e.g. sha256
        this.resetPasswordToken = resetToken;

        this.resetPasswordExpire = LocalDateTime.now().plusMinutes(RESET_PASSWORD_TOKEN_EXPIRY);

        return resetToken;
    }
}
