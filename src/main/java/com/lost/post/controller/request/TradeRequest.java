package com.lost.post.controller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TradeRequest {

    @NotEmpty
    private Long traderId;

    public TradeRequest(Long traderId) {
        this.traderId = traderId;
    }
}
