package com.lost.post.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.fake.TestContainer;
import com.lost.post.domain.Address;
import com.lost.post.domain.LostItem;
import com.lost.post.domain.PostStatus;
import com.lost.post.domain.TradeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostServiceTest {

    private PostService postService;
    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        testContainer = new TestContainer();
        postService = new PostService(testContainer.postRepository);
    }

    @Test
    @DisplayName("게시글 조회 성공")
    void should_succeed_when_find_exist_post() {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();
        testContainer.userRepository.save(user);

        Post post = Post.builder()
                .title("This is post title")
                .content("This is post content")
                .tradeType(TradeType.DIRECT)
                .writer(user)
                .lostItem(LostItem.builder()
                        .name("airpods")
                        .address(Address.builder()
                                .street("서울시 광진구 아차산로")
                                .latitude(37.123)
                                .longitude(127.456)
                                .build())
                        .build())
                .reward(10_000)
                .status(PostStatus.PENDING)
                .build();
        post = testContainer.postRepository.save(post);
        //when
        Post findPost = postService.postDetails(post.getId());
        //then
        assertAll(
                () -> assertThat(findPost.getTitle()).isEqualTo("This is post title"),
                () -> assertThat(findPost.getContent()).isEqualTo("This is post content"),
                () -> assertThat(findPost.getTradeType()).isEqualTo(TradeType.DIRECT),
                () -> assertThat(findPost.getTradeType()).isEqualTo(TradeType.DIRECT),
                () -> assertThat(findPost.getStatus()).isEqualTo(PostStatus.PENDING),
                () -> assertThat(findPost.getLostItem().getName()).isEqualTo("airpods"),
                () -> assertThat(findPost.getLostItem().getAddress().getLatitude()).isEqualTo(37.123),
                () -> assertThat(findPost.getLostItem().getAddress().getLongitude()).isEqualTo(127.456),
                () -> assertThat(findPost.getLostItem().getAddress().getStreet()).isEqualTo("서울시 광진구 아차산로")
        );
    }

    @Test
    @DisplayName("게시글 생성 실패 - 존재하지 않는 게시글")
    void should_fail_when_find_non_existent_post() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.postDetails(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}