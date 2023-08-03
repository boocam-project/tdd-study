package com.swger.tddstudy.user.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserLevel {

    BRONZE("브론즈", 0), SILVER("실버", 1), GOLD("골드", 2);

    private final String text;
    private final int level;

    public UserLevel levelUp() {
        UserLevel[] levels = values();
        if (this.level >= levels.length - 1) {
            return this;
        }
        return levels[this.level + 1];
    }
}
