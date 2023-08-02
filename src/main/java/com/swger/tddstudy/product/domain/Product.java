package com.swger.tddstudy.product.domain;

import com.swger.tddstudy.util.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @Min(1000)
    private int price;

    @Min(1)
    private int amount;

    @Enumerated(EnumType.STRING)
    private SellingStatus sellingStatus;

    public Product(String name, int price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.sellingStatus = SellingStatus.SELLING;
    }
    public void sellProduct(){
        this.amount -= 1;
    }
    public void sellStop(){
        this.sellingStatus = SellingStatus.STOP_SELLING;
    }
    public void sellStart(){
        this.sellingStatus = SellingStatus.SELLING;
    }

}
