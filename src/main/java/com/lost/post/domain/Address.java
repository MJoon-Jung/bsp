package com.lost.post.domain;

import com.lost.common.domain.exception.InvalidRequestException;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {

    @NotBlank
    private String street;
    private Double latitude;
    private Double longitude;

    @Builder
    public Address(String street, Double latitude, Double longitude) {
        validate(latitude, longitude);
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void validate(Double latitude, Double longitude) {
        InvalidRequestException invalidRequestException = new InvalidRequestException();
        if (latitude < -90.0 || latitude > 90.0) {
            invalidRequestException.addValidation("latitude", "위도는 -90.0 ~ 90.0 사이의 값이어야 합니다.");
        }
        if (longitude < -180.0 || longitude > 180.0) {
            invalidRequestException.addValidation("longitude", "경도는 -180.0 ~ 180.0 사이의 값이어야 합니다.");
        }
        if (invalidRequestException.getValidation().size() > 0) {
            throw invalidRequestException;
        }
    }
}
