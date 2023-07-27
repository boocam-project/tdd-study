package com.swger.tddstudy.member.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberLevel {

    BRONZE("브론즈"), SILVER("실버"), GOLD("골드");

    private final String text;
}
