package com.lost.post.controller.request;

import lombok.Getter;

@Getter
public class MapArea {

    private final Location southwest;
    private final Location southeast;
    private final Location northwest;
    private final Location northeast;

    public MapArea(PostsLoadCondition postsLoadCondition) {
        southwest = Location.from(postsLoadCondition.getSouthwestLongitude(),
                postsLoadCondition.getSouthwestLatitude());
        southeast = Location.from(postsLoadCondition.getNortheastLongitude(),
                postsLoadCondition.getSouthwestLatitude());
        northwest = Location.from(postsLoadCondition.getSouthwestLongitude(),
                postsLoadCondition.getNortheastLatitude());
        northeast = Location.from(postsLoadCondition.getNortheastLongitude(),
                postsLoadCondition.getNortheastLatitude());
    }
}
