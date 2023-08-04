package com.swger.tddstudy.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.swger.tddstudy.product.domain.Product;
import com.swger.tddstudy.product.domain.SellingStatus;
import com.swger.tddstudy.product.exception.ProductNotFoundException;
import com.swger.tddstudy.product.repository.ProductRepository;
import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.domain.UserDto;
import com.swger.tddstudy.user.domain.UserLevel;
import com.swger.tddstudy.user.domain.UserType;
import com.swger.tddstudy.user.exception.LoginFailureException;
import com.swger.tddstudy.user.repository.UserRepository;
import com.swger.tddstudy.user.request.JoinRequest;
import com.swger.tddstudy.user.service.UserService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Transactional
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Nested
    @DisplayName("상품 판매 상태 변경 : ")
    class ProductSellingStatusUpdateTest {

        private Product newSoldOutProduct() {
            return Product.builder().id(0L).name("product").price(340000).amount(0)
                .sellingStatus(SellingStatus.SELLING).build();
        }

        private Product newProduct() {
            return Product.builder().id(0L).name("product").price(340000).amount(10)
                .sellingStatus(SellingStatus.SELLING).build();
        }

        @Test
        @DisplayName("재고가 소진 되어 제품 판매 중지")
        void Test1() {

            // given
            Optional<Product> optionalProduct = Optional.ofNullable(newSoldOutProduct());
            given(productRepository.findById(any(Long.class))).willReturn(optionalProduct);

            // when
            String message = productService.sellingStatusUpdate(0L);

            // then
            assertThat(message).isEqualTo("마지막 재고가 소진 되어 상품 판매가 중지 됩니다.");

            verify(productRepository, times(1)).findById(any(Long.class));
        }

        @Test
        @DisplayName("재고가 소진 되지 않아 판매 지속")
        void Test2() {

            // given
            Optional<Product> optionalProduct = Optional.ofNullable(newProduct());
            given(productRepository.findById(any(Long.class))).willReturn(optionalProduct);

            // when
            String message = productService.sellingStatusUpdate(0L);

            // then
            assertThat(message).isEqualTo("판매가 계속 됩니다.");

            verify(productRepository, times(1)).findById(any(Long.class));
        }

        @Test
        @DisplayName("")
        void Test3() {

            // given
            Optional<Product> optionalProduct = Optional.empty();
            given(productRepository.findById(any(Long.class))).willReturn(optionalProduct);

            // when, then
            Throwable exception = assertThrows(ProductNotFoundException.class, () -> {
                productService.sellingStatusUpdate(0L);
            });
            assertEquals("일치하는 상품이 없습니다.", exception.getMessage());
            verify(productRepository, times(1)).findById(any(Long.class));
        }
    }
}
