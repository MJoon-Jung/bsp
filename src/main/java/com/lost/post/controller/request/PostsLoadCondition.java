package com.lost.post.controller.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostsLoadCondition {

    private Double southwestLongitude;
    private Double southwestLatitude;
    private Double northeastLongitude;
    private Double northeastLatitude;

    @Builder
    public PostsLoadCondition(Double southwestLongitude, Double southwestLatitude, Double northeastLongitude,
            Double northeastLatitude) {
        this.southwestLongitude = southwestLongitude;
        this.southwestLatitude = southwestLatitude;
        this.northeastLongitude = northeastLongitude;
        this.northeastLatitude = northeastLatitude;
    }
}
