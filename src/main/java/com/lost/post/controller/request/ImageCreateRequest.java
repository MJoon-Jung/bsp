package com.lost.post.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lost.image.domain.PostImage;
import com.lost.post.domain.Post;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonDeserialize(using = ImageCreateRequestDeserializer.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageCreateRequest {

    @Valid
    @JsonValue
    private List<ImageCreate> imageCreate;

    @Builder
    @JsonCreator
    public ImageCreateRequest(List<ImageCreate> imageCreate) {
        this.imageCreate = imageCreate;
    }

    public boolean isEmpty() {
        return Objects.isNull(imageCreate) || imageCreate.isEmpty();
    }

    public List<ImageCreate> getImageCreate() {
        return Collections.unmodifiableList(imageCreate);
    }

    public List<PostImage> toEntity(Post post) {
        return imageCreate.stream()
                .map(image -> image.toEntity(post))
                .toList();
    }
}
