package com.lost.post.domain;

import com.lost.image.domain.PostImage;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LostItem {

    private final String name;
    private final Address address;
    private final List<PostImage> images;

    @Builder
    public LostItem(String name, Address address, List<PostImage> images) {
        this.name = name;
        this.address = address;
        this.images = images;
    }
}
