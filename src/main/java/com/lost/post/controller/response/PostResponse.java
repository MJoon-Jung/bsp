package com.lost.post.controller.response;

import com.lost.post.domain.LostItem;
import com.lost.post.domain.PostStatus;
import com.lost.post.domain.TradeType;
import com.lost.user.controller.response.WriterResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final Integer reward;
    private final TradeType tradeType;
    private final WriterResponse writer;
    private final LostItem lostItem;
    private final PostStatus status;
    private final LocalDateTime createdAt;

    @Builder
    public PostResponse(Long id, String title, String content, Integer reward, TradeType tradeType,
            WriterResponse writer,
            LostItem lostItem, PostStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.reward = reward;
        this.tradeType = tradeType;
        this.writer = writer;
        this.lostItem = lostItem;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .reward(post.getReward())
                .tradeType(post.getTradeType())
                .writer(WriterResponse.from(post.getWriter()))
                .lostItem(post.getLostItem())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
