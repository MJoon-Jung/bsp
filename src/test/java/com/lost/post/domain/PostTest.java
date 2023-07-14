package com.lost.post.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.lost.post.controller.request.ImageCreate;
import com.lost.post.controller.request.ImageCreateRequest;
import com.lost.post.controller.request.PostUpdateRequest;
import com.lost.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    @DisplayName("게시글 생성 성공")
    void should_be_success_when_create_new_post() {
        //given
        Post post = Post.builder()
                .id(1L)
                .content("example post content")
                .tradeType(TradeType.DIRECT)
                .user(User.builder()
                        .id(1L)
                        .email("example@email.com")
                        .nickname("example")
                        .password("password")
                        .build())
                .itemName("AirPods")
                .address(JpaAddress.from(Address.builder()
                        .street("서울시 광진구 아차산로")
                        .latitude(37.123)
                        .longitude(127.456)
                        .build()))
                .reward(10_000)
                .status(PostStatus.PENDING)
                .build();
        //when
        //then
        assertAll(
                () -> assertThat(post.getId()).isEqualTo(1L),
                () -> assertThat(post.getContent()).isEqualTo("example post content"),
                () -> assertThat(post.getTradeType()).isEqualTo(TradeType.DIRECT),
                () -> assertThat(post.getStatus()).isEqualTo(PostStatus.PENDING),
                () -> assertThat(post.getReward()).isEqualTo(10_000),
                () -> assertThat(post.getItemName()).isEqualTo("AirPods"),
                () -> assertThat(post.getAddress().toModel().getLatitude()).isEqualTo(37.123),
                () -> assertThat(post.getAddress().toModel().getLongitude()).isEqualTo(127.456),
                () -> assertThat(post.getUser().getId()).isEqualTo(1L),
                () -> assertThat(post.getUser().getEmail()).isEqualTo("example@email.com"),
                () -> assertThat(post.getUser().getNickname()).isEqualTo("example")
        );
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void should_be_updated_when_post_is_updated() {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();
        Post post = Post.builder()
                .id(1L)
                .content("example post content")
                .tradeType(TradeType.DIRECT)
                .user(User.builder()
                        .id(1L)
                        .email("example@email.com")
                        .nickname("example")
                        .password("password")
                        .build())
                .itemName("AirPods")
                .address(JpaAddress.from(Address.builder()
                        .street("서울시 광진구 아차산로")
                        .latitude(37.123)
                        .longitude(127.456)
                        .build()))
                .reward(10_000)
                .status(PostStatus.PENDING)
                .build();

        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .content("example post content2")
                .tradeType(TradeType.ALL)
                .reward(1_000)
                .itemName(post.getItemName())
                .address(Address.builder()
                        .street("서울시 광진구 아차산로 2길")
                        .latitude(37.124)
                        .longitude(127.457)
                        .build())
                .imageCreateRequest(new ImageCreateRequest(List.of(
                        ImageCreate.builder()
                                .url("http://localhost:8080/images/aipods.jpg")
                                .originalFileName("airpods.jpg")
                                .fileName("ABCDEFGHIJKLMN_airpods.jpg")
                                .build()
                )))
                .build();
        //when
        post.update(postUpdateRequest, user.getId());
        //then
        assertAll(
                () -> assertThat(post.getId()).isEqualTo(1L),
                () -> assertThat(post.getContent()).isEqualTo("example post content2"),
                () -> assertThat(post.getTradeType()).isEqualTo(TradeType.ALL),
                () -> assertThat(post.getStatus()).isEqualTo(PostStatus.PENDING),
                () -> assertThat(post.getReward()).isEqualTo(1_000),
                () -> assertThat(post.getItemName()).isEqualTo("AirPods"),
                () -> assertThat(post.getPostImages().size()).isEqualTo(1),
                () -> assertThat(post.getAddress().toModel().getStreet()).isEqualTo("서울시 광진구 아차산로 2길"),
                () -> assertThat(post.getAddress().toModel().getLatitude()).isEqualTo(37.124),
                () -> assertThat(post.getAddress().toModel().getLongitude()).isEqualTo(127.457)
        );
    }

    @Test
    @DisplayName("게시글 수정 성공 - imageCreateRequest null 인 경우")
    void post_update_should_succeed_when_imageCreateRequest_is_a_null_value() {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();
        Post post = Post.builder()
                .id(1L)
                .content("example post content")
                .tradeType(TradeType.DIRECT)
                .user(User.builder()
                        .id(1L)
                        .email("example@email.com")
                        .nickname("example")
                        .password("password")
                        .build())
                .itemName("AirPods")
                .address(JpaAddress.from(Address.builder()
                        .street("서울시 광진구 아차산로")
                        .latitude(37.123)
                        .longitude(127.456)
                        .build()))
                .reward(10_000)
                .status(PostStatus.PENDING)
                .build();

        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .content("example post content2")
                .tradeType(TradeType.ALL)
                .reward(1_000)
                .itemName(post.getItemName())
                .address(Address.builder()
                        .street("서울시 광진구 아차산로 2길")
                        .latitude(37.124)
                        .longitude(127.457)
                        .build())
                .build();
        //when
        post.update(postUpdateRequest, user.getId());
        //then
        assertAll(
                () -> assertThat(post.getId()).isEqualTo(1L),
                () -> assertThat(post.getContent()).isEqualTo("example post content2"),
                () -> assertThat(post.getTradeType()).isEqualTo(TradeType.ALL),
                () -> assertThat(post.getStatus()).isEqualTo(PostStatus.PENDING),
                () -> assertThat(post.getReward()).isEqualTo(1_000),
                () -> assertThat(post.getItemName()).isEqualTo("AirPods"),
                () -> assertThat(post.getPostImages().size()).isEqualTo(0),
                () -> assertThat(post.getAddress().toModel().getStreet()).isEqualTo("서울시 광진구 아차산로 2길"),
                () -> assertThat(post.getAddress().toModel().getLatitude()).isEqualTo(37.124),
                () -> assertThat(post.getAddress().toModel().getLongitude()).isEqualTo(127.457)
        );
    }

    @Test
    @DisplayName("post finder, status 수정 - 분실물이 찾아졌을 때")
    void should_update_finder_and_status_when_trade_lost_item() {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();
        Post post = Post.builder()
                .id(1L)
                .content("example post content")
                .tradeType(TradeType.DIRECT)
                .user(User.builder()
                        .id(1L)
                        .email("example@email.com")
                        .nickname("example")
                        .password("password")
                        .build())
                .itemName("AirPods")
                .address(JpaAddress.from(Address.builder()
                        .street("서울시 광진구 아차산로")
                        .latitude(37.123)
                        .longitude(127.456)
                        .build()))
                .reward(10_000)
                .status(PostStatus.PENDING)
                .build();
        //when
        post.tradeItem(1L, User.builder()
                .id(2L)
                .email("example2@email.com")
                .nickname("example2")
                .password("password2")
                .build());
        //then
        assertAll(
                () -> assertThat(post.getId()).isEqualTo(1L),
                () -> assertThat(post.getContent()).isEqualTo("example post content"),
                () -> assertThat(post.getTradeType()).isEqualTo(TradeType.DIRECT),
                () -> assertThat(post.getStatus()).isEqualTo(PostStatus.END),
                () -> assertThat(post.getReward()).isEqualTo(10_000),
                () -> assertThat(post.getFinder().getId()).isEqualTo(2L),
                () -> assertThat(post.getFinder().getNickname()).isEqualTo("example2"),
                () -> assertThat(post.getFinder().getEmail()).isEqualTo("example2@email.com"),
                () -> assertThat(post.getFinder().getPassword()).isEqualTo("password2"),
                () -> assertThat(post.getItemName()).isEqualTo("AirPods"),
                () -> assertThat(post.getAddress().toModel().getLatitude()).isEqualTo(37.123),
                () -> assertThat(post.getAddress().toModel().getLongitude()).isEqualTo(127.456)
        );
    }
}