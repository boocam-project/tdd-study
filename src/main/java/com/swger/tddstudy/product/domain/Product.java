package com.swger.tddstudy.product.domain;

import com.swger.tddstudy.util.BaseEntity;

import javax.persistence.*;
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

    @NotBlank
    private int price;

    @NotBlank
    private int amount;

    @Enumerated(EnumType.STRING)
    private SellingStatus sellingStatus;

    public Product(String name, int price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        if(amount >0) this.sellingStatus = SellingStatus.SELLING;
        else this.sellingStatus = SellingStatus.STOP_SELLING;
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
