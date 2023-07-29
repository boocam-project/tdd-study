package com.swger.tddstudy.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserLevel {

    BRONZE("브론즈", 0), SILVER("실버", 1), GOLD("골드", 2);

    private final String text;
    private final int level;

    UserLevel levelUp() {
        UserLevel[] levels = values();

        if (!isMaxLevel()) {
            return levels[this.level + 1];
        }

        return this;
    }

    private boolean isMaxLevel() {
        return this.level >= values().length - 1;
    }
}
