package com.lost.post.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    @DisplayName("게시글 생성 성공")
    void should_be_success_when_create_new_post() {
        //given
        Post post = Post.builder()
                .id(1L)
                .title("example post title")
                .content("example post content")
                .tradeType(TradeType.DIRECT)
                .writer(User.builder()
                        .id(1L)
                        .email("example@email.com")
                        .nickname("example")
                        .password("password")
                        .build())
                .lostItem(LostItem.builder()
                        .name("AirPods")
                        .address(Address.builder()
                                .street("서울시 광진구 아차산로")
                                .latitude(37.123)
                                .longitude(127.456)
                                .build())
                        .build())
                .reward(10_000)
                .status(PostStatus.PENDING)
                .build();
        //when
        //then
        assertAll(
                () -> assertThat(post.getId()).isEqualTo(1L),
                () -> assertThat(post.getTitle()).isEqualTo("example post title"),
                () -> assertThat(post.getContent()).isEqualTo("example post content"),
                () -> assertThat(post.getTradeType()).isEqualTo(TradeType.DIRECT),
                () -> assertThat(post.getStatus()).isEqualTo(PostStatus.PENDING),
                () -> assertThat(post.getReward()).isEqualTo(10_000),
                () -> assertThat(post.getLostItem().getName()).isEqualTo("AirPods"),
                () -> assertThat(post.getLostItem().getAddress().getLatitude()).isEqualTo(37.123),
                () -> assertThat(post.getLostItem().getAddress().getLongitude()).isEqualTo(127.456),
                () -> assertThat(post.getWriter().getId()).isEqualTo(1L),
                () -> assertThat(post.getWriter().getEmail()).isEqualTo("example@email.com"),
                () -> assertThat(post.getWriter().getNickname()).isEqualTo("example")
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
                .title("example post title")
                .content("example post content")
                .tradeType(TradeType.DIRECT)
                .writer(user)
                .lostItem(LostItem.builder()
                        .name("AirPods")
                        .address(Address.builder()
                                .street("서울시 광진구 아차산로")
                                .latitude(37.123)
                                .longitude(127.456)
                                .build())
                        .build())
                .reward(10_000)
                .status(PostStatus.PENDING)
                .build();
        //when
        Post updatePost = post.update("example post title2", "example post content2", TradeType.ALL, LostItem.builder()
                .name("AirPods")
                .address(Address.builder()
                        .street("서울시 관악구 xx로")
                        .latitude(37.124)
                        .longitude(127.457)
                        .build())
                .build(), user);
        //then
        assertAll(
                () -> assertThat(updatePost.getId()).isEqualTo(1L),
                () -> assertThat(updatePost.getTitle()).isEqualTo("example post title2"),
                () -> assertThat(updatePost.getContent()).isEqualTo("example post content2"),
                () -> assertThat(updatePost.getTradeType()).isEqualTo(TradeType.ALL),
                () -> assertThat(updatePost.getStatus()).isEqualTo(PostStatus.PENDING),
                () -> assertThat(updatePost.getReward()).isEqualTo(10_000),
                () -> assertThat(updatePost.getLostItem().getName()).isEqualTo("AirPods"),
                () -> assertThat(updatePost.getLostItem().getAddress().getLatitude()).isEqualTo(37.124),
                () -> assertThat(updatePost.getLostItem().getAddress().getLongitude()).isEqualTo(127.457)
        );
    }

    @Test
    @DisplayName("post finder, status 수정 - 분실물이 찾아졌을 때")
    void should_update_finder_and_status_when_lost_item_is_found() {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("example post title")
                .content("example post content")
                .tradeType(TradeType.DIRECT)
                .writer(user)
                .lostItem(LostItem.builder()
                        .name("AirPods")
                        .address(Address.builder()
                                .street("서울시 광진구 아차산로")
                                .latitude(37.123)
                                .longitude(127.456)
                                .build())
                        .build())
                .reward(10_000)
                .status(PostStatus.PENDING)
                .build();
        //when
        Post findPost = post.found(PostStatus.END, User.builder()
                .id(2L)
                .email("example2@email.com")
                .nickname("example2")
                .password("password2")
                .build());
        //then
        assertAll(
                () -> assertThat(findPost.getId()).isEqualTo(1L),
                () -> assertThat(findPost.getTitle()).isEqualTo("example post title"),
                () -> assertThat(findPost.getContent()).isEqualTo("example post content"),
                () -> assertThat(findPost.getTradeType()).isEqualTo(TradeType.DIRECT),
                () -> assertThat(findPost.getStatus()).isEqualTo(PostStatus.END),
                () -> assertThat(findPost.getReward()).isEqualTo(10_000),
                () -> assertThat(findPost.getFinder().getId()).isEqualTo(2L),
                () -> assertThat(findPost.getFinder().getNickname()).isEqualTo("example2"),
                () -> assertThat(findPost.getFinder().getEmail()).isEqualTo("example2@email.com"),
                () -> assertThat(findPost.getFinder().getPassword()).isEqualTo("password2"),
                () -> assertThat(findPost.getLostItem().getName()).isEqualTo("AirPods"),
                () -> assertThat(findPost.getLostItem().getAddress().getLatitude()).isEqualTo(37.123),
                () -> assertThat(findPost.getLostItem().getAddress().getLongitude()).isEqualTo(127.456)
        );
    }
}