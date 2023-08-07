package com.swger.tddstudy.product.repository;

import com.swger.tddstudy.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
