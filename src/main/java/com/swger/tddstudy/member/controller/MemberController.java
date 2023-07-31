package com.swger.tddstudy.member.controller;

import com.swger.tddstudy.member.domain.Member;
import com.swger.tddstudy.member.domain.MemberDTO;
import com.swger.tddstudy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
//    signIn이 아니라 SignUp이 맞는 표현.. 영어 이슈
    @PostMapping("/signIn")
    public String signIn(@RequestBody MemberDTO memberDTO) {
        /* 비밀번호 재확인 */
        if (!memberDTO.getPassword().equals(memberDTO.getRePassword())) {
            return "Password Mismatch";
        }
        Member newMember = new Member(memberDTO.getUsername(),
                memberDTO.getPassword(), memberDTO.getNickname());
        memberService.join(newMember);
        return "signInOK";
    }

}
