package com.lost.post.infra.entity;

import com.lost.common.infra.entity.BaseTimeJpaEntity;
import com.lost.image.infra.entity.ImagePostJpaEntity;
import com.lost.post.domain.Address;
import com.lost.post.domain.LostItem;
import com.lost.post.domain.Post;
import com.lost.post.domain.PostStatus;
import com.lost.post.domain.TradeType;
import com.lost.user.infra.entity.UserJpaEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "POST")
@Entity
public class PostJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Integer reward;
    private String itemName;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private PostStatus status;
    @Enumerated(EnumType.STRING)
    private TradeType tradeType;
    @OneToMany(mappedBy = "postJpaEntity")
    private List<ImagePostJpaEntity> imagePostJpaEntities = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserJpaEntity userJpaEntity;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINDER_ID")
    private UserJpaEntity finder;

    public Post toModel() {
        return Post.builder()
                .id(getId())
                .title(title)
                .content(content)
                .reward(reward)
                .tradeType(tradeType)
                .writer(getUserJpaEntity().toModel())
                .lostItem(LostItem.builder()
                        .name(itemName)
                        .address(address)
                        .images(imagePostJpaEntities.stream()
                                .map(ImagePostJpaEntity::toModel)
                                .toList())
                        .build())
                .status(status)
                .finder(finder != null ? finder.toModel() : null)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static PostJpaEntity from(Post post) {
        PostJpaEntity postJpaEntity = new PostJpaEntity();
        postJpaEntity.id = post.getId();
        postJpaEntity.title = post.getTitle();
        postJpaEntity.content = post.getContent();
        postJpaEntity.reward = post.getReward();
        postJpaEntity.itemName = post.getLostItem().getName();
        postJpaEntity.address = post.getLostItem().getAddress();
        postJpaEntity.status = post.getStatus();
        postJpaEntity.tradeType = post.getTradeType();
        if (post.getLostItem().getImages() != null) {
            postJpaEntity.imagePostJpaEntities = post.getLostItem().getImages().stream()
                    .map((image) -> ImagePostJpaEntity.from(image, postJpaEntity))
                    .toList();
        }
        postJpaEntity.userJpaEntity = UserJpaEntity.from(post.getWriter());
        if (post.getFinder() != null) {
            postJpaEntity.finder = UserJpaEntity.from(post.getFinder());
        }
        postJpaEntity.setCreatedAt(post.getCreatedAt());
        postJpaEntity.setUpdatedAt(post.getUpdatedAt());
        return postJpaEntity;
    }
}