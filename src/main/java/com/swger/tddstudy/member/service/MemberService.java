package com.swger.tddstudy.member.service;

import com.swger.tddstudy.member.domain.Member;
import com.swger.tddstudy.member.domain.DTO.MemberDTO;
import com.swger.tddstudy.member.domain.DTO.MemberSignInDTO;
import com.swger.tddstudy.member.domain.MemberType;
import com.swger.tddstudy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Member join(Member member){
        memberRepository.save(member);
        return member;
    }
    public Member SignUp(MemberDTO member){
        if(!member.getPassword().equals(member.getRePassword())){
            throw new IllegalArgumentException("RePassword Mismatch");
        }
        Member signUpMember = new Member(member.getUsername(), member.getPassword(), member.getNickname());
        join(signUpMember);
        return signUpMember;
    }
    public Member SignIn(MemberSignInDTO member){
        Member signInMember = memberRepository.findByUsername(member.getUsername()).orElseThrow(() -> new IllegalArgumentException("Username Mismatch"));
        if (!signInMember.getPassword().equals(member.getPassword())) {
            throw new IllegalArgumentException("Password Mismatch");
        }
        return signInMember;
    }
}
