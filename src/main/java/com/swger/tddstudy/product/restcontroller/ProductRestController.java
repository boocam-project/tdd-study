package com.swger.tddstudy.product.restcontroller;

import com.swger.tddstudy.product.domain.Product;
import com.swger.tddstudy.product.exception.UnauthorizedException;
import com.swger.tddstudy.product.request.ProductAddRequest;
import com.swger.tddstudy.product.request.ProductStockUpRequest;
import com.swger.tddstudy.product.service.ProductService;
import com.swger.tddstudy.user.request.JoinRequest;
import com.swger.tddstudy.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/product")
public class ProductRestController {

    private final ProductService productService;
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> join(@Valid @RequestBody ProductAddRequest productAddRequest,
        HttpServletRequest request) {
        Map<String, Object> message = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        if (userService.isAdmin((Long) session.getAttribute("id"))) {
            message.put("status", 200);
            message.put("data", productService.productAdd(productAddRequest));
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            throw new UnauthorizedException("관리자만 상품을 등록할 수 있습니다.");
        }
    }

    @PostMapping("/stock-up")
    public ResponseEntity<?> join(@Valid @RequestBody ProductStockUpRequest productStockUpRequest,
        HttpServletRequest request) {
        Map<String, Object> message = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        if (userService.isAdmin((Long) session.getAttribute("id"))) {
            message.put("status", 200);
            message.put("data", productService.productStockUp(productStockUpRequest));
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            throw new UnauthorizedException("관리자만 상품 재고를 추가할 수 있습니다.");
        }
    }
}
