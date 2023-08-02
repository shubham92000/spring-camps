package com.bootcamp.springcamp.dtos.bootcamp;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailReqDto {
    @NotNull
    private String email;
}
