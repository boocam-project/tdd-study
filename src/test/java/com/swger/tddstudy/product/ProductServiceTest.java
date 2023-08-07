package com.swger.tddstudy.product;

import com.swger.tddstudy.product.domain.DTO.ProductRegisterDTO;
import com.swger.tddstudy.product.domain.Product;
import com.swger.tddstudy.product.repository.ProductRepository;
import com.swger.tddstudy.product.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;


@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    @DisplayName("상품 등록이 가능합니다")
    @Test
    public void productRegisterSuccess() {
        //given
        ProductRegisterDTO product = new ProductRegisterDTO("testName", 1000, 10);
        //when
        Product registeredProduct = productService.register(product);
        Optional<Product> productOptional = productRepository.findById(registeredProduct.getId());
        //then
        Assertions.assertThat(registeredProduct.getId())
            .isEqualTo(productOptional.get().getId());
    }


    @DisplayName("상품 주문이 가능합니다")
    @Test
    public void sellingSuccess() {
        //given
        Product product = new Product("testName", 1000, 10);
        //when, then
        Assertions.assertThat(product.sellingOrNo()).isTrue();
    }

    @DisplayName("상품 주문이 불가능합니다 - 재고 부족1")
    @Test
    public void sellingFailAmount1() {
        //given
        Product product = new Product("testName", 1000, 0);
        //when, then
        Assertions.assertThat(product.sellingOrNo()).isFalse();
    }

    @DisplayName("상품 주문이 불가능합니다 - 재고 부족2")
    @Test
    public void sellingFailAmount2() {
        //given
        Product product = new Product("testName", 1000, 1);
        //when
        product.sellProduct(1);
        //then
        Assertions.assertThat(product.sellingOrNo()).isFalse();
    }


}
