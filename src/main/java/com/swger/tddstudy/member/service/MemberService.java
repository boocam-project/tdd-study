package com.swger.tddstudy.member.service;

import com.swger.tddstudy.member.domain.Member;
import com.swger.tddstudy.member.domain.Member;
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
}
