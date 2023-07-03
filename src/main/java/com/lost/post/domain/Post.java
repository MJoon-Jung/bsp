package com.lost.post.domain;

import com.lost.common.domain.exception.UnauthorizedException;
import com.lost.common.infra.entity.BaseTimeJpaEntity;
import com.lost.image.domain.PostImage;
import com.lost.user.domain.User;
import jakarta.persistence.CascadeType;
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
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "POST")
@Entity
public class Post extends BaseTimeJpaEntity {

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
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINDER_ID")
    private User finder;

    @Builder(toBuilder = true)
    public Post(Long id, String title, String content, Integer reward, String itemName, Address address,
            PostStatus status,
            TradeType tradeType, List<PostImage> postImages, User user, User finder) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.reward = reward;
        this.itemName = itemName;
        this.address = address;
        this.status = status;
        this.tradeType = tradeType;
        if (postImages != null) {
            this.postImages = postImages;
        }
        this.user = user;
        user.getPosts().add(this);
        this.finder = finder;
    }

    public void update(Long userId) {
        if (Objects.equals(user.getId(), userId)) {
            throw new UnauthorizedException();
        }
    }

    public Post found(PostStatus status, User findUser) {
        return this.toBuilder()
                .status(status)
                .finder(findUser)
                .build();
    }
}