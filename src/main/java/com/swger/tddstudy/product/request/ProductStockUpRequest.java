package com.swger.tddstudy.product.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockUpRequest {

    @NotNull(message = "상품 ID를 입력하세요.")
    private Long id;

    @NotNull(message = "상품 수량을 입력하세요.")
    @Min(value = 1, message = "상품 수량은 1 이상으로 입력하세요.")
    private int amount;

}
