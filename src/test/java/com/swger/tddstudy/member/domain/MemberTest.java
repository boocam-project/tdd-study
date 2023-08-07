package com.swger.tddstudy.member.domain;

import com.swger.tddstudy.member.domain.DTO.MemberDTO;
import com.swger.tddstudy.member.repository.MemberRepository;
import com.swger.tddstudy.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class MemberTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @DisplayName("등급 업")
    @Test
    public void LevelUp(){
        //given
        Member member = memberService.join(new Member("testUsername", "testPassword", "testNickName"));
        //when
        member.LevelUp();
        //then
        Assertions.assertThat(member.getMemberLevel()).isEqualTo(MemberLevel.SILVER);
    }

}
