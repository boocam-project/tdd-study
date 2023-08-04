package com.swger.tddstudy.product.request;

import com.swger.tddstudy.product.domain.ProductDto;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddRequest {

    @NotNull(message = "상품명을 입력하세요.")
    @Size(min = 2, max = 10, message = "상품명을 2자 이상 30자 이하로 입력하세요.")
    private String name;

    @NotNull(message = "상품 가격을 입력하세요.")
    @Min(value = 0, message = "상품 가격은 0 이상으로 입력하세요.")
    private int price;

    @NotNull(message = "상품 수량을 입력하세요.")
    @Min(value = 1, message = "상품 수량은 1 이상으로 입력하세요.")
    private int amount;

    public ProductDto toProductDto() {
        return ProductDto.builder().name(this.name).price(this.price).amount(this.amount).build();
    }
}
