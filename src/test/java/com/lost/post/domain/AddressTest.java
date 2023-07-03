//package com.lost.post.domain;
//
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//import com.lost.common.domain.exception.InvalidRequestException;
//import com.lost.post.domain.Address.AddressBuilder;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//class AddressTest {
//
//    @Test
//    @DisplayName("게시글 생성 성공 - 이미지 첨부 X")
//    void create() {
//        Address.builder()
//                .latitude(38.123456)
//                .longitude(126.123456)
//                .street("서울시 광진구 자양동 123-123")
//                .build();
//    }
//    @Test
//    @DisplayName("게시글 생성 성공 - 이미지 첨부 X")
//    void create2() {
//        AddressBuilder builder = Address.builder()
//                .latitude(38.123456)
//                .longitude(190.0)
//                .street("서울시 광진구 자양동 123-123");
//
//        assertThatThrownBy(builder::build)
//                .isInstanceOf(InvalidRequestException.class);
//    }
//}