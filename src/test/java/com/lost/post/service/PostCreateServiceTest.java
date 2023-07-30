package com.lost.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.image.service.FileFinder;
import com.lost.post.controller.request.ImageCreate;
import com.lost.post.controller.request.ImageCreateRequest;
import com.lost.post.controller.request.PostCreateRequest;
import com.lost.post.controller.response.PostResponse;
import com.lost.post.domain.Address;
import com.lost.post.domain.Post;
import com.lost.post.domain.TradeType;
import com.lost.post.infra.repository.PostRepository;
import com.lost.user.domain.User;
import com.lost.user.domain.UserRole;
import com.lost.user.service.repostiory.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class PostCreateServiceTest {

    @Autowired
    private PostCreateService postCreateService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private FileFinder fileFinder;

    @Test
    @DisplayName("게시글 생성 - 성공")
    void postCreateService_create_return_post_response() {
        //given
        User user = User.builder()
                .email("example@email.com")
                .nickname("example")
                .password("examplepassword")
                .role(UserRole.MEMBER)
                .build();
        user = userRepository.save(user);

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .content("This is new content")
                .tradeType(TradeType.DIRECT)
                .reward(1_000)
                .itemName("airpods")
                .address(Address.builder()
                        .latitude(38.123456)
                        .longitude(126.123456)
                        .street("서울시 광진구 자양동 123-123")
                        .build())
                .imageCreateRequest(ImageCreateRequest.builder()
                        .imageCreate(List.of(
                                ImageCreate.builder()
                                        .url("http://localhost:8080/images/ABCDEFGHIJKLMNOPQRSTUVWXYZ_test.jpg")
                                        .fileName("ABCDEFGHIJKLMNOPQRSTUVWXYZ_test.jpg")
                                        .originalFileName("test.jpg")
                                        .build()))
                        .build())
                .build();

        given(fileFinder.find(any(String.class)))
                .willReturn(null);

        //when
        PostResponse postResponse = postCreateService.create(user.getId(), postCreateRequest);
        List<Post> posts = postRepository.findAll();
//        //then
        assertAll(
                () -> assertThat(posts.size()).isEqualTo(1),
                () -> assertThat(postResponse.getContent()).isEqualTo("This is new content"),
                () -> assertThat(postResponse.getTradeType()).isEqualTo(TradeType.DIRECT),
                () -> assertThat(postResponse.getReward()).isEqualTo(1_000),
                () -> assertThat(postResponse.getItemName()).isEqualTo("airpods"),
                () -> assertThat(postResponse.getAddress().getLatitude()).isEqualTo(38.123456),
                () -> assertThat(postResponse.getAddress().getLongitude()).isEqualTo(126.123456),
                () -> assertThat(postResponse.getAddress().getStreet()).isEqualTo("서울시 광진구 자양동 123-123"),
                () -> assertThat(postResponse.getImages().size()).isEqualTo(1),
                () -> assertThat(postResponse.getImages().get(0).getUrl()).isEqualTo(
                        "http://localhost:8080/images/ABCDEFGHIJKLMNOPQRSTUVWXYZ_test.jpg"),
                () -> assertThat(postResponse.getImages().get(0).getFileName()).isEqualTo(
                        "ABCDEFGHIJKLMNOPQRSTUVWXYZ_test.jpg"),
                () -> assertThat(postResponse.getImages().get(0).getOriginalFileName()).isEqualTo("test.jpg")
        );
    }

    @Test
    @DisplayName("게시글 생성 요청 시 존재하지 않는 유저 아이디를 PathVariable 로 넘기면 예외가 발생한다.")
    void postCreateService_create_should_fail_if_user_does_not_exist() {
        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .content("This is new content")
                .tradeType(TradeType.DIRECT)
                .reward(1_000)
                .itemName("airpods")
                .address(Address.builder()
                        .latitude(38.123456)
                        .longitude(126.123456)
                        .street("서울시 광진구 자양동 123-123")
                        .build())
                .imageCreateRequest(ImageCreateRequest.builder()
                        .imageCreate(List.of(
                                ImageCreate.builder()
                                        .url("http://localhost:8080/images/ABCDEFGHIJKLMNOPQRSTUVWXYZ_test.jpg")
                                        .fileName("ABCDEFGHIJKLMNOPQRSTUVWXYZ_test.jpg")
                                        .originalFileName("test.jpg")
                                        .build()))
                        .build())
                .build();
        //when
        //then
        assertThatThrownBy(() -> postCreateService.create(1L, postCreateRequest))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("게시글 생성 요청 시 존재하지 않는 파일을 첨부하면 예외가 발생한다.")
    void postCreateService_create_should_fail_if_file_does_not_exist() {
        //given
        User user = User.builder()
                .email("example@email.com")
                .nickname("example")
                .password("examplepassword")
                .role(UserRole.MEMBER)
                .build();

        userRepository.save(user);

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .content("This is new content")
                .tradeType(TradeType.DIRECT)
                .reward(1_000)
                .itemName("airpods")
                .address(Address.builder()
                        .latitude(38.123456)
                        .longitude(126.123456)
                        .street("서울시 광진구 자양동 123-123")
                        .build())
                .imageCreateRequest(ImageCreateRequest.builder()
                        .imageCreate(List.of(
                                ImageCreate.builder()
                                        .url("http://localhost:8080/images/ABCDEFGHIJKLMNOPQRSTUVWXYZ_test.jpg")
                                        .fileName("ABCDEFGHIJKLMNOPQRSTUVWXYZ_test.jpg")
                                        .originalFileName("test.jpg")
                                        .build()))
                        .build())
                .build();

        given(fileFinder.find(any(String.class)))
                .willThrow(new ResourceNotFoundException("IMAGE", "fileName", "ABCDEFGHIJKLMNOPQRSTUVWXYZ_test.jpg"));

        //when
        //then
        assertThatThrownBy(() -> postCreateService.create(user.getId(), postCreateRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("[IMAGE]: fileName ABCDEFGHIJKLMNOPQRSTUVWXYZ_test.jpg를 찾을 수 없습니다.");
    }
}