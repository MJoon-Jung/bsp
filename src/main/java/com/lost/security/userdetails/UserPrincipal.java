package com.lost.security.userdetails;

import java.util.Set;
import org.springframework.security.core.userdetails.User;

public class UserPrincipal extends User {

    private final Long userId;

    public UserPrincipal(com.lost.user.domain.User user) {
        super(user.getEmail(), user.getPassword(), Set.of());
        userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
