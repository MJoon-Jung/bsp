package com.lost.post.controller.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Location {

    private Double longitude;
    private Double latitude;

    @Builder
    public Location(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static Location from(Double longitude, Double latitude) {
        return Location.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }
}
