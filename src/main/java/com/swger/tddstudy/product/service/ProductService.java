package com.swger.tddstudy.product.service;

import com.swger.tddstudy.product.domain.DTO.ProductRegisterDTO;
import com.swger.tddstudy.product.domain.Product;
import com.swger.tddstudy.product.domain.SellingStatus;
import com.swger.tddstudy.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.TableView;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product register(ProductRegisterDTO product) {
        Product save = productRepository.save(new Product(product.getName()
            , product.getPrice(), product.getAmount()));
        return save;
    }

}
