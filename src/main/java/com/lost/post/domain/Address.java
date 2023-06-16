package com.lost.post.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String street;
    private Double latitude;
    private Double longitude;

    @Builder
    public Address(String street, Double latitude, Double longitude) {
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
