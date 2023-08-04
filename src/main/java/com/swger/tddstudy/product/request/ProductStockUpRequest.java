package com.swger.tddstudy.product.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockUpRequest {

    @NotBlank(message = "상품 ID를 입력하세요.")
    private Long id;

    @NotBlank(message = "상품 수량을 입력하세요.")
    @Min(value = 0, message = "상품 수량은 0 이상으로 입력하세요.")
    private int amount;

}
