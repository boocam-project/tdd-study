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

    public boolean sellingOrNo(Product product) {
        /* 처음부터 재고 수량 = 0 */
        if (product.getSellingStatus()== SellingStatus.STOP_SELLING) return false;
        else {
            /* 구매 가능 */
            if (product.getAmount() > 0 && product.getSellingStatus()== SellingStatus.SELLING){
                return true;
            }
            else {
                /* 재고수량이 처음으로 0이 되었을 때 */
                product.sellStop();
                return false;
            }
        }
    }


}
