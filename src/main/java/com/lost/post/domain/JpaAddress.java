package com.lost.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

@Slf4j
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaAddress {

    private String street;
    @Column(columnDefinition = "geometry SRID 0")
    private Point location;

    @Builder
    public JpaAddress(String street, Point location) {
        this.street = street;
        this.location = location;
    }

    public Address toModel() {
        return Address.builder()
                .street(street)
                .latitude(location.getX())
                .longitude(location.getY())
                .build();
    }

    public static JpaAddress from(Address address) {
        Coordinate coordinate = new Coordinate(address.getLatitude(), address.getLongitude());
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(coordinate);
        return JpaAddress.builder()
                .street(address.getStreet())
                .location(point)
                .build();
    }
}
