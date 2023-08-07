package com.swger.tddstudy.member.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberType {

    Member("일반회원"), ADMIN("관리자");

    private final String text;

}
