package com.swger.tddstudy.product.controller;

import com.swger.tddstudy.product.domain.DTO.ProductRegisterDTO;
import com.swger.tddstudy.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/productRegister")
    public String productRegister(@Validated @RequestBody ProductRegisterDTO DTO, BindingResult br) throws BindException {
        if (br.hasErrors()) throw new BindException(br);
        productService.register(DTO);
        return "Register OK";
    }
}
