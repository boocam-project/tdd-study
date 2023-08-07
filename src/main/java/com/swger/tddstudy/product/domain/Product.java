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
    public boolean sellProduct(int cnt){
        /* 사용자가 재고 이상으로 주문 */
        if (cnt > this.amount) {
            return false;
        }
        /* 사용자가 재고에 맞게 주문 */
        this.amount -= cnt;
        return true;
    }
    public void sellStop(){
        this.sellingStatus = SellingStatus.STOP_SELLING;
    }
    public void sellStart(){
        this.sellingStatus = SellingStatus.SELLING;
    }

    public boolean sellingOrNo() {
        /* 처음부터 재고 수량 = 0 */
        if (this.getSellingStatus() == SellingStatus.STOP_SELLING) {
            return false;
        }
        /* 구매 가능 */
        if (this.getAmount() > 0 && this.getSellingStatus() == SellingStatus.SELLING) {
            return true;}
        else {
            /* 재고수량이 처음으로 0이 되었을 때 */
            this.sellStop();
            return false;
        }
    }

}
