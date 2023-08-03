package com.bootcamp.springcamp.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "currencies")
@CompoundIndexes({
        @CompoundIndex(
                name = "country_currencyCode",
                def = "{'country' : 1, 'currencyCode': 1}"
        )
})
public class Currency {
    @Id
    private String id;
    @NotEmpty
    private String currencyCode;
    @NotEmpty
    private String country;

    public Currency(String currencyCode, String country) {
        this.currencyCode = currencyCode;
        this.country = country;
    }
}
