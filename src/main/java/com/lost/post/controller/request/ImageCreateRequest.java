package com.lost.post.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonRootName(value = "images")
@JsonDeserialize(using = ImageCreateRequestDeserializer.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageCreateRequest {

    @Valid
    @JsonProperty("images")
    private List<ImageCreate> imageCreate;

    @Builder
    public ImageCreateRequest(List<ImageCreate> imageCreate) {
        this.imageCreate = imageCreate;
    }

    public boolean isEmpty() {
        return Objects.isNull(imageCreate) || imageCreate.isEmpty();
    }

    public List<ImageCreate> getImageCreate() {
        return Collections.unmodifiableList(imageCreate);
    }

    public List<Image> toModel() {
        return imageCreate.stream()
                .map(ImageCreate::toImage)
                .toList();
    }
}
