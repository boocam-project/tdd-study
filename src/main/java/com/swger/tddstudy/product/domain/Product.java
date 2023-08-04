package com.swger.tddstudy.product.domain;

import com.swger.tddstudy.util.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Entity
@Table(name = "Products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @NonNull
    @Column(name = "price", nullable = false)
    private int price;

    @NonNull
    @Column(name = "amount", nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    private SellingStatus sellingStatus;

    public ProductDto toProductDto() {
        return ProductDto.builder().id(this.id).name(this.name).price(this.price)
            .amount(this.amount).sellingStatus(this.sellingStatus.name()).build();
    }

}
