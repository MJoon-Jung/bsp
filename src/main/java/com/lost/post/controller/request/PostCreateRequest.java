package com.lost.post.controller.request;

import com.lost.post.domain.Address;
import com.lost.post.domain.TradeType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateRequest {

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;
    @NotBlank(message = "본문은 필수 입력 값입니다.")
    private String content;
    private TradeType tradeType;
    @Min(value = 1_000, message = "보상은 1,000원 이상이어야 합니다.")
    private Integer reward;
    @NotBlank(message = "물품명은 필수 입력 값입니다.")
    private String itemName;
    @Valid
    private Address address;
    private List<Long> images;

    @Builder
    public PostCreateRequest(String title, String content, TradeType tradeType, Integer reward, String itemName,
            Address address, List<Long> images) {
        this.title = title;
        this.content = content;
        this.tradeType = tradeType;
        this.reward = reward;
        this.itemName = itemName;
        this.address = address;
        this.images = images;
    }
}
