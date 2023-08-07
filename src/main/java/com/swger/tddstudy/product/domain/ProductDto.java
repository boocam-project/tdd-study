package com.swger.tddstudy.product.domain;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private int price;
    private int amount;
    private String sellingStatus;

    public ProductDto(String name, int price, int amount, String sellingStatus) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.sellingStatus = sellingStatus;
    }

    public Product toEntity() {
        return Product.builder().name(this.name).price(this.price).amount(this.amount)
            .sellingStatus(SellingStatus.valueOf(this.sellingStatus)).build();
    }
}
