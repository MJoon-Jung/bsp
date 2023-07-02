package com.lost.post.domain;

import com.lost.common.domain.exception.UnauthorizedException;
import com.lost.post.controller.request.ImageCreateRequest;
import com.lost.post.controller.request.PostCreateRequest;
import com.lost.user.domain.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

    private final Long id;
    private final String title;
    private final String content;
    private final Integer reward;
    private final TradeType tradeType;
    private final User writer;
    private final LostItem lostItem;
    private final PostStatus status;
    private final User finder;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder(toBuilder = true)
    public Post(Long id, String title, String content, Integer reward, TradeType tradeType, User writer,
            LostItem lostItem,
            PostStatus status, User finder, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.reward = reward;
        this.tradeType = tradeType;
        this.writer = writer;
        this.lostItem = lostItem;
        this.status = status;
        this.finder = finder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Post update(final String title, final String content, final TradeType tradeType, final LostItem lostItem,
            final User user) {
        if (!writer.equals(user)) {
            throw new UnauthorizedException();
        }
        return this.toBuilder()
                .title(title)
                .content(content)
                .tradeType(tradeType)
                .lostItem(lostItem)
                .build();
    }

    public Post found(final PostStatus status, final User findUser) {
        return this.toBuilder()
                .status(status)
                .finder(findUser)
                .build();
    }

    public static Post from(PostCreateRequest postCreateRequest, User user) {
        return Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .reward(postCreateRequest.getReward())
                .tradeType(postCreateRequest.getTradeType())
                .writer(user)
                .lostItem(LostItem.builder()
                        .name(postCreateRequest.getItemName())
                        .address(postCreateRequest.getAddress())
                        .build())
                .status(PostStatus.PENDING)
                .build();
    }

    public static Post from(PostCreateRequest postCreateRequest, User user, ImageCreateRequest imageCreateRequest) {
        return Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .reward(postCreateRequest.getReward())
                .tradeType(postCreateRequest.getTradeType())
                .writer(user)
                .lostItem(LostItem.builder()
                        .name(postCreateRequest.getItemName())
                        .address(postCreateRequest.getAddress())
                        .images(imageCreateRequest.toModel())
                        .build())
                .status(PostStatus.PENDING)
                .build();
    }
}
