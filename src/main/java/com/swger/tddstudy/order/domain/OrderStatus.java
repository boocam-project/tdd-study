package com.swger.tddstudy.order.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {

    INIT("주문생성"),
    CANCELED("주문취소"),
    COMPLETED("처리완료");

    private final String text;
}
