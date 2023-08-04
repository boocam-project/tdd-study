package com.swger.tddstudy.product.service;

import com.swger.tddstudy.product.domain.Product;
import com.swger.tddstudy.product.domain.ProductDto;
import com.swger.tddstudy.product.domain.SellingStatus;
import com.swger.tddstudy.product.exception.ProductNotFoundException;
import com.swger.tddstudy.product.repository.ProductRepository;
import com.swger.tddstudy.user.domain.User;
import com.swger.tddstudy.user.exception.LoginFailureException;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 재고가 다 떨어졌다면, 상품의 판매 상태를 STOP_SELLING으로 변경
    public String sellingStatusUpdate(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getAmount() == 0) {
                product.setSellingStatus(SellingStatus.STOP_SELLING);
                return "마지막 재고가 소진 되어 상품 판매가 중지 됩니다.";
            } else {
                return "판매가 계속 됩니다.";
            }
        } else {
            throw new ProductNotFoundException("일치하는 상품이 없습니다.");
        }
    }
}
