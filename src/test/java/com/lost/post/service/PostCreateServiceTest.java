package com.lost.post.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.fake.TestContainer;
import com.lost.image.infra.ResourceFinder;
import com.lost.post.controller.request.ImageCreate;
import com.lost.post.controller.request.ImageCreateRequest;
import com.lost.post.controller.request.PostCreateRequest;
import com.lost.post.domain.Address;
import com.lost.post.domain.TradeType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PostCreateServiceTest {

    private PostCreateService postCreateService;
    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        testContainer = new TestContainer();
        postCreateService = new PostCreateService(
                testContainer.postRepository,
                testContainer.imagePostRepository,
                testContainer.userRepository,
                testContainer.imageConfig
        );
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
        Post post = postCreateService.create(user.getId(), postCreateRequest);
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
                .fileName("123123airpods.jpg")
                .originalFileName("airpods.jpg")
                .build();
        testContainer.imagePostRepository.save(image);

        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();
        testContainer.userRepository.save(user);
        ImageCreate imageCreate = ImageCreate.builder()
                .url("http://localhost:8080/images/upload/abcdef_abc.jpg")
                .originalFileName("abc.jpg")
                .fileName("abcdef_abc.jpg")
                .build();
        ImageCreate imageCreate2 = ImageCreate.builder()
                .url("http://localhost:8080/images/upload/abcdef2_bac.jpg")
                .originalFileName("abc.jpg")
                .fileName("abcdef_abc.jpg")
                .build();
        ImageCreate imageCreate3 = ImageCreate.builder()
                .url("http://localhost:8080/images/upload/abcdef3_cab.jpg")
                .originalFileName("abc.jpg")
                .fileName("abcdef_abc.jpg")
                .build();

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("This is new title")
                .content("This is new content")
                .tradeType(TradeType.DIRECT)
                .reward(1_000)
                .itemName("airpods")
                .imageCreateRequest(ImageCreateRequest.builder()
                        .imageCreate(List.of(imageCreate, imageCreate2, imageCreate3))
                        .build())
                .address(Address.builder()
                        .latitude(38.123456)
                        .longitude(126.123456)
                        .street("서울시 광진구 자양동 123-123")
                        .build())
                .build();
        //when
        ResourceFinder resourceFinder = Mockito.mock(ResourceFinder.class);
        given(resourceFinder.find("images/upload/abcdef", "abc.jpg"))
                .willReturn(null);
        Post post = postCreateService.create(user.getId(), postCreateRequest);
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
                () -> assertThat(post.getLostItem().getImages().size()).isEqualTo(3),
                () -> assertThat(post.getLostItem().getImages().get(0).getFileName()).isEqualTo("abcdef_abc.jpg"),
                () -> assertThat(post.getLostItem().getImages().get(0).getOriginalFileName()).isEqualTo("abc.jpg")
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
        assertThatThrownBy(() -> postCreateService.create(1L, postCreateRequest))
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
        ImageCreate imageCreate = ImageCreate.builder()
                .url("http://localhost:8080/images/upload/abcdef_abc.jpg")
                .originalFileName("abc.jpg")
                .fileName("abcdef_abc.jpg")
                .build();
        ImageCreate imageCreate2 = ImageCreate.builder()
                .url("http://localhost:8080/images/upload/abcdef2_bac.jpg")
                .originalFileName("abc.jpg")
                .fileName("abcdef_abc.jpg")
                .build();
        ImageCreate imageCreate3 = ImageCreate.builder()
                .url("http://localhost:8080/images/upload/abcdef3_cab.jpg")
                .originalFileName("abc.jpg")
                .fileName("abcdef_abc.jpg")
                .build();

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("This is new title")
                .content("This is new content")
                .tradeType(TradeType.DIRECT)
                .reward(1_000)
                .itemName("airpods")
                .imageCreateRequest(ImageCreateRequest.builder()
                        .imageCreate(List.of(imageCreate, imageCreate2, imageCreate3))
                        .build())
                .address(Address.builder()
                        .latitude(38.123456)
                        .longitude(126.123456)
                        .street("서울시 광진구 자양동 123-123")
                        .build())
                .build();
        //when
        assertThatThrownBy(() -> postCreateService.create(1L, postCreateRequest))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}