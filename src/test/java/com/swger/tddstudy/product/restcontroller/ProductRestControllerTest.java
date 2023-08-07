package com.swger.tddstudy.product.restcontroller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swger.tddstudy.product.domain.ProductDto;
import com.swger.tddstudy.product.request.ProductAddRequest;
import com.swger.tddstudy.product.request.ProductStockUpRequest;
import com.swger.tddstudy.product.service.ProductService;
import com.swger.tddstudy.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductRestController.class)
public class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    UserService userService;

    @Nested
    @DisplayName("상품 등록 컨트롤러 : ")
    class ProductAddControllerTest {

        private ProductDto newProductDto() {
            return ProductDto.builder().id(0L).name("product").price(340000).amount(10)
                .sellingStatus("SELLING").build();
        }

        private ProductAddRequest newProductAddRequest() {
            return ProductAddRequest.builder().name("product").price(340000).amount(10).build();
        }

        private ProductAddRequest newWrongProductAddRequest() {
            return ProductAddRequest.builder().name("product").price(340000).amount(0).build();
        }

        @Test
        @DisplayName("양식이 맞을 경우 성공")
        void Test1() throws Exception {

            // given
            given(productService.productAdd(any(ProductAddRequest.class))).willReturn(
                newProductDto());
            MockHttpSession session = new MockHttpSession();
            session.setAttribute("id", 0L);
            session.setAttribute("username", "abc");
            given(userService.isAdmin(any(Long.class))).willReturn(true);
            String json = new ObjectMapper().writeValueAsString(newProductAddRequest());

            // when, then
            mockMvc.perform(post("/api/product/add").session(session).content(json)
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").exists())
                .andExpect(jsonPath("$.data.price").exists())
                .andExpect(jsonPath("$.data.amount").exists())
                .andExpect(jsonPath("$.data.sellingStatus").exists()).andDo(print());
            verify(productService, times(1)).productAdd(any(ProductAddRequest.class));
        }

        @Test
        @DisplayName("양식에 맞지 않을 경우 실패")
        void Test2() throws Exception {

            // given
            given(productService.productAdd(any(ProductAddRequest.class))).willReturn(
                newProductDto());
            MockHttpSession session = new MockHttpSession();
            session.setAttribute("id", 0L);
            session.setAttribute("username", "abc");
            given(userService.isAdmin(any(Long.class))).willReturn(true);
            String json = new ObjectMapper().writeValueAsString(newWrongProductAddRequest());

            // when, then
            mockMvc.perform(post("/api/product/add").session(session).content(json)
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists()).andDo(print());
            verify(productService, never()).productAdd(any(ProductAddRequest.class));
        }

        @Test
        @DisplayName("양식에 맞으나 관리자가 아니면 실패")
        void Test3() throws Exception {

            // given
            given(productService.productAdd(any(ProductAddRequest.class))).willReturn(
                newProductDto());
            MockHttpSession session = new MockHttpSession();
            session.setAttribute("id", 0L);
            session.setAttribute("username", "abc");
            given(userService.isAdmin(any(Long.class))).willReturn(false);
            String json = new ObjectMapper().writeValueAsString(newProductAddRequest());

            // when, then
            mockMvc.perform(post("/api/product/add").session(session).content(json)
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").exists()).andDo(print());
            verify(productService, never()).productAdd(any(ProductAddRequest.class));
        }
    }

    @Nested
    @DisplayName("상품 재고 추가 컨트롤러 : ")
    class ProductStockUpControllerTest {

        private ProductDto newProductDto() {
            return ProductDto.builder().id(0L).name("product").price(340000).amount(10)
                .sellingStatus("SELLING").build();
        }

        private ProductStockUpRequest newProductStockUpRequest() {
            return ProductStockUpRequest.builder().id(0L).amount(10).build();
        }

        private ProductStockUpRequest newWrongProductStockUpRequest() {
            return ProductStockUpRequest.builder().id(0L).amount(0).build();
        }

        @Test
        @DisplayName("양식이 맞을 경우 성공")
        void Test1() throws Exception {

            // given
            given(productService.productStockUp(any(ProductStockUpRequest.class))).willReturn(
                newProductDto());
            MockHttpSession session = new MockHttpSession();
            session.setAttribute("id", 0L);
            session.setAttribute("username", "abc");
            given(userService.isAdmin(any(Long.class))).willReturn(true);
            String json = new ObjectMapper().writeValueAsString(newProductStockUpRequest());

            // when, then
            mockMvc.perform(post("/api/product/stock-up").session(session).content(json)
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").exists())
                .andExpect(jsonPath("$.data.price").exists())
                .andExpect(jsonPath("$.data.amount").exists())
                .andExpect(jsonPath("$.data.sellingStatus").exists()).andDo(print());
            verify(productService, times(1)).productStockUp(any(ProductStockUpRequest.class));
        }

        @Test
        @DisplayName("양식에 맞지 않을 경우 실패")
        void Test2() throws Exception {

            // given
            given(productService.productStockUp(any(ProductStockUpRequest.class))).willReturn(
                newProductDto());
            MockHttpSession session = new MockHttpSession();
            session.setAttribute("id", 0L);
            session.setAttribute("username", "abc");
            given(userService.isAdmin(any(Long.class))).willReturn(true);
            String json = new ObjectMapper().writeValueAsString(newWrongProductStockUpRequest());

            // when, then
            mockMvc.perform(post("/api/product/stock-up").session(session).content(json)
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists()).andDo(print());
            verify(productService, never()).productStockUp(any(ProductStockUpRequest.class));
        }

        @Test
        @DisplayName("양식에 맞으나 관리자가 아니면 실패")
        void Test3() throws Exception {

            // given
            given(productService.productStockUp(any(ProductStockUpRequest.class))).willReturn(
                newProductDto());
            MockHttpSession session = new MockHttpSession();
            session.setAttribute("id", 0L);
            session.setAttribute("username", "abc");
            given(userService.isAdmin(any(Long.class))).willReturn(false);
            String json = new ObjectMapper().writeValueAsString(newProductStockUpRequest());

            // when, then
            mockMvc.perform(post("/api/product/stock-up").session(session).content(json)
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").exists()).andDo(print());
            verify(productService, never()).productStockUp(any(ProductStockUpRequest.class));
        }
    }
}

