package com.swger.tddstudy.product.domain.DTO;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
public class ProductRegisterDTO {
    @NotBlank
    private String name;

    @Min(1000)
    private int price;

    @Min(1)
    private int amount;

    public ProductRegisterDTO(String name, int price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
    public ProductRegisterDTO(){}

}
