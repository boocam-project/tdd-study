package com.swger.tddstudy.product.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SellingStatus {

    SELLING("판매 중"),
    STOP_SELLING("판매 중지");

    private final String text;
}
