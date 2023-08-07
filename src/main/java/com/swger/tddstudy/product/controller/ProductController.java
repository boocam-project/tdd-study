package com.swger.tddstudy.product.controller;

import com.swger.tddstudy.member.domain.MemberType;
import com.swger.tddstudy.member.repository.MemberRepository;
import com.swger.tddstudy.member.service.MemberService;
import com.swger.tddstudy.product.domain.DTO.ProductRegisterDTO;
import com.swger.tddstudy.product.service.ProductService;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.ManagedEntity;
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

    private final ProductService productService;
    private final MemberRepository memberRepository;

    @PostMapping("/productRegister")
    public String productRegister(@Validated @RequestBody ProductRegisterDTO DTO, BindingResult br,
        HttpSession httpSession) throws BindException, AuthenticationException {
        if (br.hasErrors()) throw new BindException(br);
        productService.register(DTO);

        if (httpSession.getAttribute("id") == null) {
            throw new AuthenticationException("You must SignIn");
        }

        if(!memberRepository.findById((Long) httpSession.getAttribute("id"))
            .get().getMemberType().equals(MemberType.ADMIN)){
            throw new AuthenticationException("You are not Admin");
        }
        return "Register OK";
    }
}
