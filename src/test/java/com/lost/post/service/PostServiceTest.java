package com.lost.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.post.domain.Address;
import com.lost.post.domain.JpaAddress;
import com.lost.post.domain.Post;
import com.lost.post.domain.PostStatus;
import com.lost.post.domain.TradeType;
import com.lost.post.infra.repository.PostRepository;
import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("존재하는 게시글을 조회하면 성공적으로 게시글을 반환한다.")
    void postService_load_return_exist_post() {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();
        userRepository.save(user);

        Post post = Post.builder()
                .content("This is post content")
                .tradeType(TradeType.DIRECT)
                .user(user)
                .itemName("airpods")
                .reward(10_000)
                .status(PostStatus.PENDING)
                .address(JpaAddress.from(Address.builder()
                        .street("서울시 광진구 아차산로")
                        .latitude(37.123)
                        .longitude(127.456)
                        .build()))
                .build();
        post = postRepository.save(post);
        //when
        Post findPost = postService.load(post.getId());
        //then
        assertAll(
                () -> assertThat(findPost.getContent()).isEqualTo("This is post content"),
                () -> assertThat(findPost.getTradeType()).isEqualTo(TradeType.DIRECT),
                () -> assertThat(findPost.getTradeType()).isEqualTo(TradeType.DIRECT),
                () -> assertThat(findPost.getStatus()).isEqualTo(PostStatus.PENDING),
                () -> assertThat(findPost.getItemName()).isEqualTo("airpods"),
                () -> assertThat(findPost.getAddress().toModel().getLatitude()).isEqualTo(37.123),
                () -> assertThat(findPost.getAddress().toModel().getLongitude()).isEqualTo(127.456),
                () -> assertThat(findPost.getAddress().toModel().getStreet()).isEqualTo("서울시 광진구 아차산로")
        );
    }

    @Test
    @DisplayName("존재하지 않는 게시글을 조회 요청하면 예외가 발생한다.")
    void should_fail_when_find_non_existent_post() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.load(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}