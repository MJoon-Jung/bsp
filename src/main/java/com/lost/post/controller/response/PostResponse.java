package com.lost.post.controller.response;

import com.lost.post.domain.Address;
import com.lost.post.domain.Post;
import com.lost.post.domain.PostStatus;
import com.lost.post.domain.TradeType;
import com.lost.user.controller.response.WriterResponse;
import java.time.LocalDateTime;
import java.util.List;
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
    private final String itemName;
    private final Address address;
    private final List<PostImageResponse> images;
    private final PostStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public PostResponse(Long id, String title, String content, Integer reward, TradeType tradeType,
            WriterResponse writer,
            String itemName, Address address, List<PostImageResponse> images, PostStatus status,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.reward = reward;
        this.tradeType = tradeType;
        this.writer = writer;
        this.itemName = itemName;
        this.address = address;
        this.images = images;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .reward(post.getReward())
                .tradeType(post.getTradeType())
                .writer(WriterResponse.from(post.getUser()))
                .itemName(post.getItemName())
                .address(post.getAddress())
                .images(post.getPostImages().stream().map(PostImageResponse::from).toList())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
