package com.bootcamp.springcamp.dtos.currency;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateCurrencyReqDto {
    private String country;
}
