package com.swger.tddstudy.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swger.tddstudy.member.domain.DTO.MemberDTO;
import com.swger.tddstudy.member.domain.Member;
import com.swger.tddstudy.member.service.MemberService;
import com.swger.tddstudy.product.domain.DTO.ProductRegisterDTO;
import com.swger.tddstudy.product.repository.ProductRepository;
import com.swger.tddstudy.product.service.ProductService;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Autowired
    MemberService memberService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper om;

    @DisplayName("상품 등록이 가능합니다")
    @Test
    public void registerSuccess() throws Exception {
        //given
        ProductRegisterDTO product = new ProductRegisterDTO("testName", 1000, 10);
        Member member = memberService.SignUpAdmin(new MemberDTO("testUsername",
            "testPassword", "testNickname", "testPassword"));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", member.getId());
        //when, then
        String content = om.writeValueAsString(product);
        mockMvc.perform(post("/productRegister")
                .session(session)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Register OK"))
            .andDo(print());

    }
    @DisplayName("상품 등록이 불가능합니다- 로그인을 하지 않음")
    @Test
    public void registerFailNotLogin() throws Exception {
        //given
        ProductRegisterDTO product = new ProductRegisterDTO("testName", 1000, 10);
        MockHttpSession session = new MockHttpSession();
        //when, then
        String content = om.writeValueAsString(product);
        mockMvc.perform(post("/productRegister")
                .session(session)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("You must SignIn"))
            .andDo(print());

    }
    @DisplayName("상품 등록이 불가능합니다- Admin 계정이 아님")
    @Test
    public void registerFailNotAdmin() throws Exception {
        //given
        ProductRegisterDTO product = new ProductRegisterDTO("testName", 1000, 10);
        Member member = memberService.SignUp(new MemberDTO("testUsername",
            "testPassword", "testNickname", "testPassword"));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", member.getId());
        //when, then
        String content = om.writeValueAsString(product);
        mockMvc.perform(post("/productRegister")
                .session(session)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("You are not Admin"))
            .andDo(print());

    }


    @DisplayName("상품 등록이 불가능합니다 - NotBlank")
    @Test
    public void registerFailNotBlank() throws Exception {
        //given
        ProductRegisterDTO product = new ProductRegisterDTO(null, 1000, 10);
        //when, then
        String content = om.writeValueAsString(product);
        mockMvc.perform(post("/productRegister")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException()
                .getClass().isAssignableFrom(BindException.class)))
            .andDo(print());
    }

    @DisplayName("상품 등록이 불가능합니다 - Min")
    @Test
    public void registerFailMin() throws Exception {
        //given
        ProductRegisterDTO product = new ProductRegisterDTO("testname", 10000, 0);
        //when, then
        String content = om.writeValueAsString(product);
        mockMvc.perform(post("/productRegister")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException()
                .getClass().isAssignableFrom(BindException.class)))
            .andDo(print());
    }

}
