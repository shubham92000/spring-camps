package com.bootcamp.springcamp.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Photo {
    private String id;
    private String etag;
    private String photoUrl;
}
