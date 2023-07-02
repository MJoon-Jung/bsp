package com.lost.post.domain;

import com.lost.common.domain.exception.UnauthorizedException;
import com.lost.common.infra.entity.BaseTimeJpaEntity;
import com.lost.image.domain.PostImage;
import com.lost.user.domain.User;
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
    @OneToMany(mappedBy = "post")
    private List<PostImage> postImageJpaEntities = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINDER_ID")
    private User finder;

    public Post update(final String title, final String content, final TradeType tradeType, final LostItem lostItem,
            final User user) {
        if (!writer.equals(user)) {
            throw new UnauthorizedException();
        }
        return this.toBuilder()
                .title(title)
                .content(content)
                .tradeType(tradeType)
                .lostItem(lostItem)
                .build();
    }

    public Post found(final PostStatus status, final User findUser) {
        return this.toBuilder()
                .status(status)
                .finder(findUser)
                .build();
    }
}