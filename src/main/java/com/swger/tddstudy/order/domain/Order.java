package com.swger.tddstudy.order.domain;

import com.swger.tddstudy.orderProduct.domain.OrderProduct;
import com.swger.tddstudy.util.BaseEntity;
import com.swger.tddstudy.member.domain.Member;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {

    @Id
    private Long id;

    @ManyToOne
    private Member Member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    private int price;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
