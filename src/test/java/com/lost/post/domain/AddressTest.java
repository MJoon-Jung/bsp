package com.lost.post.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lost.common.domain.exception.InvalidRequestException;
import com.lost.post.domain.Address.AddressBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AddressTest {

    @ParameterizedTest
    @CsvSource(value = {"-91, 0", "91, 0", "0, -181", "0, 181"}, delimiter = ',')
    @DisplayName("위도/경도 범위를 넘어서면 에러가 발생한다.")
    void should_throw_an_error_if_the_range_of_latitude_and_longitude_is_exceeded(Double latitude, Double longitude) {
        AddressBuilder builder = Address.builder()
                .latitude(latitude)
                .longitude(longitude)
                .street("서울시 광진구 자양동 123-123");

        assertThatThrownBy(builder::build)
                .isInstanceOf(InvalidRequestException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"-90, 0", "90, 0", "0, -180", "0, 180", "0, 0"}, delimiter = ',')
    @DisplayName("위도/경도 범위를 넘어서지 않으면 에러가 발생하지 않는다.")
    void create2(Double latitude, Double longitude) {
        AddressBuilder builder = Address.builder()
                .latitude(latitude)
                .longitude(longitude)
                .street("서울시 광진구 자양동 123-123");

        assertThatCode(builder::build).doesNotThrowAnyException();
    }
}