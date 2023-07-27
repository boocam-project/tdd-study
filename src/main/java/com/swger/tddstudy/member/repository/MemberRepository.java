package com.swger.tddstudy.member.repository;

import com.swger.tddstudy.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    List<Member> findByUsername(String  username);

    @Override
    <S extends Member> S save(S entity);
}
