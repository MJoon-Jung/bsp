package com.lost.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.fake.TestContainer;
import com.lost.image.domain.FileType;
import com.lost.image.domain.Image;
import com.lost.post.controller.request.PostCreateRequest;
import com.lost.post.domain.Address;
import com.lost.post.domain.Post;
import com.lost.post.domain.TradeType;
import com.lost.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostCreateServiceTest {

    private PostCreateService postCreateService;
    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        testContainer = new TestContainer();
        postCreateService = new PostCreateService(testContainer.postRepository,
                testContainer.imagePostRepository, testContainer.userRepository);
    }

    @Test
    @DisplayName("게시글 생성 성공 - 이미지 첨부 X")
    void create() {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();
        testContainer.userRepository.save(user);

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("This is new title")
                .content("This is new content")
                .tradeType(TradeType.DIRECT)
                .reward(1_000)
                .itemName("airpods")
                .address(Address.builder()
                        .latitude(38.123456)
                        .longitude(126.123456)
                        .street("서울시 광진구 자양동 123-123")
                        .build())
                .build();
        //when
        Post post = postCreateService.create(postCreateRequest, user.getId());
        //then
        assertAll(
                () -> assertThat(post.getTitle()).isEqualTo("This is new title"),
                () -> assertThat(post.getContent()).isEqualTo("This is new content"),
                () -> assertThat(post.getTradeType()).isEqualTo(TradeType.DIRECT),
                () -> assertThat(post.getReward()).isEqualTo(1_000),
                () -> assertThat(post.getLostItem().getName()).isEqualTo("airpods"),
                () -> assertThat(post.getLostItem().getAddress().getLatitude()).isEqualTo(38.123456),
                () -> assertThat(post.getLostItem().getAddress().getLongitude()).isEqualTo(126.123456),
                () -> assertThat(post.getLostItem().getAddress().getStreet()).isEqualTo("서울시 광진구 자양동 123-123")
        );
    }

    @Test
    @DisplayName("게시글 생성 성공 - 이미지 첨부 O")
    void create2() {
        //given
        Image image = Image.builder()
                .url("https://example.com/image.jpg")
                .fileName("airpods.jpg")
                .fileSize(1000L)
                .fileType(FileType.JPG)
                .build();
        testContainer.imagePostRepository.save(image);

        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();
        testContainer.userRepository.save(user);

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("This is new title")
                .content("This is new content")
                .tradeType(TradeType.DIRECT)
                .reward(1_000)
                .itemName("airpods")
                .images(List.of(1L))
                .address(Address.builder()
                        .latitude(38.123456)
                        .longitude(126.123456)
                        .street("서울시 광진구 자양동 123-123")
                        .build())
                .build();
        //when
        Post post = postCreateService.create(postCreateRequest, user.getId());
        //then
        assertAll(
                () -> assertThat(post.getTitle()).isEqualTo("This is new title"),
                () -> assertThat(post.getContent()).isEqualTo("This is new content"),
                () -> assertThat(post.getTradeType()).isEqualTo(TradeType.DIRECT),
                () -> assertThat(post.getReward()).isEqualTo(1_000),
                () -> assertThat(post.getLostItem().getName()).isEqualTo("airpods"),
                () -> assertThat(post.getLostItem().getAddress().getLatitude()).isEqualTo(38.123456),
                () -> assertThat(post.getLostItem().getAddress().getLongitude()).isEqualTo(126.123456),
                () -> assertThat(post.getLostItem().getAddress().getStreet()).isEqualTo("서울시 광진구 자양동 123-123"),
                () -> assertThat(post.getLostItem().getImages().size()).isEqualTo(1),
                () -> assertThat(post.getLostItem().getImages().get(0).getFileName()).isEqualTo("airpods.jpg")
        );
    }

    @Test
    @DisplayName("게시글 생성 실패 - 유저가 존재하지 않음")
    void test1() {
        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("This is new title")
                .content("This is new content")
                .tradeType(TradeType.DIRECT)
                .reward(1_000)
                .itemName("airpods")
                .address(Address.builder()
                        .latitude(38.123456)
                        .longitude(126.123456)
                        .street("서울시 광진구 자양동 123-123")
                        .build())
                .build();
        //when
        assertThatThrownBy(() -> postCreateService.create(postCreateRequest, 1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("게시글 생성 실패 - 존재하지 않는 이미지 사용")
    void test2() {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();
        testContainer.userRepository.save(user);

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("This is new title")
                .content("This is new content")
                .tradeType(TradeType.DIRECT)
                .reward(1_000)
                .itemName("airpods")
                .images(List.of(1L, 2L, 3L))
                .address(Address.builder()
                        .latitude(38.123456)
                        .longitude(126.123456)
                        .street("서울시 광진구 자양동 123-123")
                        .build())
                .build();
        //when
        assertThatThrownBy(() -> postCreateService.create(postCreateRequest, 1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}