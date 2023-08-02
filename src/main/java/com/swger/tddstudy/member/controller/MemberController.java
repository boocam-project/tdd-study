package com.swger.tddstudy.member.controller;

import com.swger.tddstudy.member.domain.Member;
import com.swger.tddstudy.member.domain.DTO.MemberDTO;
import com.swger.tddstudy.member.domain.DTO.MemberSignInDTO;
import com.swger.tddstudy.member.repository.MemberRepository;
import com.swger.tddstudy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/signUp")
    public String signUp(@Validated @RequestBody MemberDTO memberDTO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) throw new BindException(bindingResult);
        memberService.SignUp(memberDTO);
        return "signUpOK";
    }

    @PostMapping("/logIn")
    public String logIn(@Validated @RequestBody MemberSignInDTO memberSignInDTO,BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) throw new BindException(bindingResult);
        memberService.SignIn(memberSignInDTO);
        return "LogInOK";
    }

}
