package com.swger.tddstudy.product.domain;

import com.swger.tddstudy.util.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

    @Id
    private Long id;

    private String name;

    private int price;

    private int amount;

}
