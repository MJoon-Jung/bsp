package com.lost.post.domain;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lost.common.domain.exception.InvalidRequestException;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TradeType {
    DIRECT(TRUE, FALSE), DELIVERY(FALSE, TRUE), ALL(TRUE, TRUE);

    private final Boolean direct;
    private final Boolean delivery;

    TradeType(Boolean direct, Boolean delivery) {
        this.direct = direct;
        this.delivery = delivery;
    }

    @JsonCreator
    public static TradeType forValues(@JsonProperty("direct") Boolean direct,
            @JsonProperty("delivery") Boolean delivery) {
        if (direct && delivery) {
            return ALL;
        } else if (direct) {
            return DIRECT;
        } else if (delivery) {
            return DELIVERY;
        }

        InvalidRequestException invalidRequestException = new InvalidRequestException();
        invalidRequestException.addValidation("tradeType", "직거래와 택배 중 하나는 선택해야 합니다.");
        throw invalidRequestException;
    }
}
