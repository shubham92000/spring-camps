package com.bootcamp.springcamp.dtos.currency;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrencyResDto {
    private String id;
    private String currencyCode;
    private String country;
}
