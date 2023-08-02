package com.swger.tddstudy.product.domain.DTO;

import lombok.Getter;

@Getter
public class ProductRegisterDTO {
    private String name;

    private int price;

    private int amount;

    public ProductRegisterDTO(String name, int price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

}
