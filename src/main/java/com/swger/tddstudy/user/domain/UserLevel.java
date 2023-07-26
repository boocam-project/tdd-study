package com.swger.tddstudy.user.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserLevel {

    BRONZE("브론즈"), SILVER("실버"), GOLD("골드");

    private final String text;
}
