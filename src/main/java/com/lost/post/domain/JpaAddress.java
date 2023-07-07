package com.lost.post.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaAddress {

    private String street;
    private Point point;

    @Builder
    public JpaAddress(String street, Point point) {
        this.street = street;
        this.point = point;
    }

    public Address toModel() {
        return Address.builder()
                .street(street)
                .latitude(point.getY())
                .longitude(point.getX())
                .build();
    }

    public static JpaAddress from(Address address) {
        try {
            String pointWKT = String.format("POINT(%s %s)", address.getLongitude(), address.getLatitude());
            Point point = (Point) new WKTReader().read(pointWKT);

            return JpaAddress.builder()
                    .street(address.getStreet())
                    .point(point)
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
