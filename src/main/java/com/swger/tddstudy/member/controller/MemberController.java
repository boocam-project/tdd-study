package com.swger.tddstudy.member.controller;

import com.swger.tddstudy.member.domain.Member;
import com.swger.tddstudy.member.domain.MemberDTO;
import com.swger.tddstudy.member.domain.MemberSignInDTO;
import com.swger.tddstudy.member.repository.MemberRepository;
import com.swger.tddstudy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
//    signIn이 아니라 SignUp이 맞는 표현.. 영어 이슈
    @PostMapping("/signIn")
    public String signIn(@Valid @RequestBody MemberDTO memberDTO) {
        /* 비밀번호 재확인 */
        if (!memberDTO.getPassword().equals(memberDTO.getRePassword())) {
            return "Password Mismatch";
        }
        Member newMember = new Member(memberDTO.getUsername(),
                memberDTO.getPassword(), memberDTO.getNickname());
        memberService.join(newMember);
        return "signInOK";
    }

    @PostMapping("/logIn")
    public String logIn(@RequestBody MemberSignInDTO memberSignInDTO) {
        List<Member> byUsername = memberRepository.findByUsername(memberSignInDTO.getUsername());
        if (byUsername.isEmpty()) {
            return "Username MisMatch";
        }
        if (!byUsername.get(0).getPassword().equals(memberSignInDTO.getPassword())){
            return "Password MisMatch";
        }
        return "LogInOK";
    }

}
